package ru.catn;

import java.util.Comparator;

public class Customer implements Comparable<Customer>{
    private Integer id;
    private String name;

    Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Customer o) {
        return Comparators.ID.compare(this, o);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Comparators{
        public static Comparator<Customer> NAME = new Comparator<>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.name.compareTo(o2.name);
            }
        };

        public static Comparator<Customer> ID = new Comparator<>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return Integer.compare(o1.id, o2.id);
            }
        };
    }

}
