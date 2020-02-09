package ru.catn;

public class Demo {

    public static void main(String[] args) throws NoSuchMethodException {
        TestLogging testLogging = IoC.createClass();
        testLogging.calculation(10);
    }
}
