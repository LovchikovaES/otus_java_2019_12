package ru.catn;

import java.lang.annotation.Annotation;
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

    private void executeMethods() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        int successfulCounter = 0;

        Constructor<?>[] constructors = this.testClass.getConstructors();

        Collections.shuffle(testMethods);
        for (var testMethod : testMethods) {
            Object testInstance = constructors[0].newInstance();

            Collections.shuffle(beforeMethods);
            try {
                for (var beforeMethod : beforeMethods) {
                    beforeMethod.invoke(testInstance);
                }
                try {
                    testMethod.invoke(testInstance);
                    System.out.println("Test of " + testMethod.getName() + "() successful done!");
                    successfulCounter++;
                } catch (Exception exception) {
                    System.out.println("Test of " + testMethod.getName() + "() failed! "
                            + "Cause: " + exception.getCause());
                }
            } catch (Exception exception) {
                System.out.println("Exception: " + exception.getCause());
            } finally {
                Collections.shuffle(afterMethods);
                for (var afterMethod : afterMethods) {
                    afterMethod.invoke(testInstance);
                }
            }
            System.out.println();
        }

        System.out.println("Results: "
                + successfulCounter + "(successful), "
                + (testMethods.size() - successfulCounter) + "(failed), "
                + testMethods.size() + "(all).");
    }
}
