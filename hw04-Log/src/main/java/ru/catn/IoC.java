package ru.catn;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class IoC {

    static private List<Method> logMethods = new ArrayList<>();

    public static TestLogging createClass() throws NoSuchMethodException {
        TestLogging instance;

        initLogMethods();

        if (logMethods.size() > 0) {
            InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
            instance = (TestLogging) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                    new Class<?>[]{TestLogging.class}, handler);
        } else {
            instance = new TestLoggingImpl();
        }
        return instance;
    }

    private static void initLogMethods() throws NoSuchMethodException {
        Class<?> loggingClass = TestLoggingImpl.class;
        Method[] declaredMethods = loggingClass.getDeclaredMethods();
        Class<?> loggingInterface = TestLogging.class;

        for (var declaredMethod : declaredMethods) {
            Log logAnnotation = declaredMethod.getAnnotation(Log.class);
            if (logAnnotation != null) {
                Method interfaceMethod = loggingInterface.getMethod(declaredMethod.getName(), declaredMethod.getParameterTypes());
                logMethods.add(interfaceMethod);
            }
        }
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logMethods.contains(method)) {
                System.out.print("executed method: " + method.getName()
                        + ", params:");
                for (var arg : args) {
                    System.out.print(" " + arg);
                }
                System.out.println();
            }
            return method.invoke(myClass, args);
        }
    }
}
