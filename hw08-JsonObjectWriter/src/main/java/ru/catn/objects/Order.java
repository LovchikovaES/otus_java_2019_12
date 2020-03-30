package ru.catn.objects;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Order {
    private TreeSet<Item> items;
    private Map<Item, Integer> itemQuantities;
    private List<Partner> partners;

    public Order(TreeSet<Item> items, List<Partner> partners, Map<Item, Integer> itemQuantities) {
        this.items = items;
        this.itemQuantities = itemQuantities;
        this.partners = partners;
    }
}
