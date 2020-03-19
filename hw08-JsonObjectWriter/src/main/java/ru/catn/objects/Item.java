package ru.catn.objects;

public class Item implements Comparable<Item> {
    private final int id;
    private final Product product;
    private final double price;
    private final boolean isInStock;

    public Item(int id, Product product, double price, boolean isInStock) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.isInStock = isInStock;
    }

    @Override
    public int compareTo(Item o) {
        return this.id - o.id;
    }
}

