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
    private List<Method> beforeMethods = new ArrayList();
    private List<Method> afterMethods = new ArrayList();
    private List<Method> testMethods = new ArrayList();

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
            Annotation[] annotations = declaredMethod.getAnnotations();
            for (var annotation : annotations) {
                String annotationName = annotation.annotationType().getSimpleName();

                if (annotationName.equals(Test.class.getSimpleName())) {
                    testMethods.add(declaredMethod);
                } else if (annotationName.equals(After.class.getSimpleName())) {
                    afterMethods.add(declaredMethod);
                } else if (annotationName.equals(Before.class.getSimpleName())) {
                    beforeMethods.add(declaredMethod);
                }
            }
        }
    }

    private void executeMethods() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        int successfulCounter = 0;
        int failedCounter = 0;

        Constructor<?>[] constructors = this.testClass.getConstructors();

        Collections.shuffle(testMethods);
        for (var testMethod : testMethods) {
            Object testInstance = constructors[0].newInstance();

            Collections.shuffle(beforeMethods);
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
                failedCounter++;
            }

            Collections.shuffle(afterMethods);
            for (var afterMethod : afterMethods) {
                afterMethod.invoke(testInstance);
            }

            System.out.println();
        }

        System.out.println("Results: "
                + successfulCounter + "(successful), "
                + failedCounter + "(failed), "
                + testMethods.size() + "(all).");
    }
}
