package ru.catn;

public class Calculator {
    public Calculator() {}

    public int multiply(int x, int y){
        return x*y;
    }

    public double divide(int x, int y) throws RuntimeException{
        if (y == 0) {
            throw new RuntimeException("Division by zero");
        }
        return x/y;
    }
}
