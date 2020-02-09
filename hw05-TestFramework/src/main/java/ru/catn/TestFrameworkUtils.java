package ru.catn;

public class TestFrameworkUtils {
    public static void run(String testClassName) throws Exception {
        TestFramework testFramework = new TestFramework(testClassName);
        testFramework.run();
    }
}
