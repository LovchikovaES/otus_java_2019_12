package ru.catn;

public class TestLoggingImpl implements TestLogging{
    @Override
    @Log
    public void calculation(int param) { }
}
