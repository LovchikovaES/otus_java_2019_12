package ru.catn;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestDIYArrayListCustomer {

    private static final int DEFAULT_CAPACITY = 20;
    private String[] names = {
            "Юрий", "Борис", "Илья", "Екатерина", "Анна", "Ирина", "Антон", "Сергей",
            "Денис", "Ольга", "Елена", "Яна", "Анастасия", "Павел", "Мария", "Александр",
            "Алексей", "Жанна", "Наталья", "Олеся", "Станислав", "Филипп", "Алёна"};

    List<Customer> arrayList = new DIYArrayList<>();

    public void run() {
        fillList();
        System.out.println("DIYArrayList:");
        System.out.println(arrayList);

        Customer customers[] = getRandomCustomers(3);
        System.out.println("New customers:");
        System.out.println(Arrays.toString(customers));
        Collections.addAll(arrayList, customers);
        System.out.println("DIYArrayList after Collections.addAll new customers:");
        System.out.println(arrayList);

        List<Customer> copyOfArrayList = new DIYArrayList<>(arrayList.size());
        Collections.copy(copyOfArrayList, arrayList);
        System.out.println("Copy of Array list:");
        System.out.println(copyOfArrayList);

        Collections.sort(arrayList, Customer.Comparators.NAME);
        System.out.println("DIYArrayList after sorting by Name:");
        System.out.println(arrayList);
    }

    private void fillList() {
        int id = 100;
        Random random = new Random();
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            arrayList.add(new Customer(id++, names[random.nextInt(names.length)]));
        }
    }

    private Customer[] getRandomCustomers(int quantity) {
        int id = 0;

        Customer[] customers = new Customer[quantity];
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            customers[i] = new Customer(id++, names[random.nextInt(names.length)]);
        }
        return customers;
    }

}
