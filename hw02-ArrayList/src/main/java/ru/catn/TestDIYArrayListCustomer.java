package ru.catn;

import java.util.Collections;
import java.util.Random;

public class TestDIYArrayListCustomer {

    private static final int DEFAULT_CAPACITY = 20;
    private String[] names = {
                "Юрий", "Борис", "Илья", "Екатерина", "Анна", "Ирина", "Антон", "Сергей",
                "Денис", "Ольга", "Елена", "Яна", "Анастасия", "Павел", "Мария", "Александр",
                "Алексей", "Жанна", "Наталья", "Олеся", "Станислав", "Филипп", "Алёна" };

    DIYArrayList<Customer> arrayList = new DIYArrayList<>();

    public void run() {

        fillList();
        System.out.println("DIYArrayList:");
        arrayList.print();

        Customer[] customers = new Customer[3];
        System.out.print("New customers: [");
        Random random = new Random();
        int id = 0;
        for( int i = 0; i < customers.length; i++ ) {
            customers[i] = new Customer(id++, names[random.nextInt(names.length)]);
            System.out.print(customers[i]);
            if (i + 1 < customers.length) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        Collections.addAll(arrayList, customers);
        System.out.println("DIYArrayList after Collections.addAll new customers:");
        arrayList.print();

        DIYArrayList<Customer> copyOfArrayList = new DIYArrayList<>(arrayList.size());
        Collections.copy(copyOfArrayList, arrayList);
        System.out.println("Copy of Array list:");
        copyOfArrayList.print();

        Collections.sort(arrayList, Customer.Comparators.NAME);
        System.out.println("DIYArrayList after sorting by Name:");
        arrayList.print();
    }

    private void fillList() {
        int id = 100;
        Random random = new Random();
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            arrayList.add(new Customer(id++, names[random.nextInt(names.length)]) );
        }
    }

}
