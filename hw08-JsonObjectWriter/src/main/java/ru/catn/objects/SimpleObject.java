package ru.catn.objects;

public class SimpleObject {
    private final transient boolean flag;
    private final int intNumber;
    private final double doubleNumber;
    private final Long longNumber;
    private final String stringField;
    private final char symbol;
    private final Boolean booleanField;
    private final short shortNumber;

    public SimpleObject(boolean flag, int intNumber, double doubleNumber, Long longNumber, String stringField, Boolean booleanField, short shortNumber, char symbol) {
        this.flag = flag;
        this.intNumber = intNumber;
        this.doubleNumber = doubleNumber;
        this.longNumber = longNumber;
        this.stringField = stringField;
        this.booleanField = booleanField;
        this.shortNumber = shortNumber;
        this.symbol = symbol;
    }
}
