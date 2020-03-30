package ru.catn.objects;

public class Product {
    private final int id;
    private final String name;
    private final String country;
    private transient final long skuNumber;

    public Product(int id, String name, String country, long skuNumber) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.skuNumber = skuNumber;
    }
}
