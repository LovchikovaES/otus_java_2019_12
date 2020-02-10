package ru.catn;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestFramework {

    private Class<?> testClass;
    private List<Method> beforeMethods = new ArrayList<>();
    private List<Method> afterMethods = new ArrayList<>();
    private List<Method> testMethods = new ArrayList<>();

    public TestFramework(String testClassName) throws ClassNotFoundException {
        this.testClass = Class.forName(testClassName);
    }

    public void run() throws Exception {
        initMethods();
        executeMethods();
    }

    private void initMethods() {
        Method[] declaredMethods = this.testClass.getDeclaredMethods();
        for (var declaredMethod : declaredMethods) {
            if (declaredMethod.getAnnotation(Test.class) != null) {
                testMethods.add(declaredMethod);
            } else if (declaredMethod.getAnnotation(After.class) != null) {
                afterMethods.add(declaredMethod);
            } else if (declaredMethod.getAnnotation(Before.class) != null) {
                beforeMethods.add(declaredMethod);
            }
        }
    }

    private void executeMethods() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        int successfulCounter = 0;

        Constructor<?>[] constructors = this.testClass.getConstructors();

        Collections.shuffle(testMethods);
        for (var testMethod : testMethods) {
            Object testInstance = constructors[0].newInstance();

            if (executeBeforeMethods(testInstance)) {
                if (executeTestMethod(testMethod, testInstance))
                    successfulCounter++;
            }
            executeAfterMethods(testInstance);
            System.out.println();
        }
        System.out.println("Results: "
                + successfulCounter + "(successful), "
                + (testMethods.size() - successfulCounter) + "(failed), "
                + testMethods.size() + "(all).");
    }

    private boolean executeBeforeMethods(Object testInstance) {
        Collections.shuffle(beforeMethods);
        for (var beforeMethod : beforeMethods) {
            try {
                beforeMethod.invoke(testInstance);
            } catch (Exception exception) {
                System.out.println(beforeMethod.getName() + "() failed! "
                        + "Cause: " + exception.getCause());
                return false;
            }
        }
        return true;
    }

    private boolean executeTestMethod(Method testMethod, Object testInstance) {
        try {
            testMethod.invoke(testInstance);
            System.out.println("Test of " + testMethod.getName() + "() successful done!");
        } catch (Exception exception) {
            System.out.println("Test of " + testMethod.getName() + "() failed! "
                    + "Cause: " + exception.getCause());
            return false;
        }
        return true;
    }

    private void executeAfterMethods(Object testInstance) {
        Collections.shuffle(afterMethods);
        for (var afterMethod : afterMethods) {
            try {
                afterMethod.invoke(testInstance);
            } catch (Exception exception) {
                System.out.println(afterMethod.getName() + "() failed! "
                        + "Cause: " + exception.getCause());
            }
        }
    }
}
