package ru.catn;

import java.util.Collections;

public class TestDIYArrayListInteger {

    private static final int DEFAULT_CAPACITY = 40;

    DIYArrayList<Integer> arrayList = new DIYArrayList<>();

    public void run() {
        fillList();
        System.out.println("DIYArrayList:");
        arrayList.print();

        Integer[] integerArray = {1, 2, 3};
        Collections.addAll(arrayList, integerArray);
        System.out.println("DIYArrayList after Collections.addAll [1,2,3]");
        arrayList.print();

        DIYArrayList<Integer> copyOfArrayList = new DIYArrayList<>(arrayList.size());
        Collections.copy(copyOfArrayList, arrayList);
        System.out.println("Copy of DIYArrayList after copying:");
        copyOfArrayList.print();

        Collections.sort(arrayList);
        System.out.println("DIYArrayList after sorting:");
        arrayList.print();
    }

    private void fillList() {
        int value = 100;
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            arrayList.add(value++);
        }
    }

}
