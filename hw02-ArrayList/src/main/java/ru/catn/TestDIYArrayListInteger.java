package ru.catn;

import java.util.Collections;
import java.util.List;

public class TestDIYArrayListInteger {

    private static final int DEFAULT_CAPACITY = 40;

    List<Integer> arrayList = new DIYArrayList<>();

    public void run() {
        fillList();
        System.out.println("DIYArrayList:");
        System.out.println(arrayList);

        Integer[] integerArray = {1, 2, 3};
        Collections.addAll(arrayList, integerArray);
        System.out.println("DIYArrayList after Collections.addAll [1,2,3]");
        System.out.println(arrayList);

        List<Integer> copyOfArrayList = new DIYArrayList<>(arrayList.size());
        Collections.copy(copyOfArrayList, arrayList);
        System.out.println("Copy of DIYArrayList after copying:");
        System.out.println(copyOfArrayList);

        Collections.sort(arrayList);
        System.out.println("DIYArrayList after sorting:");
        System.out.println(arrayList);
    }

    private void fillList() {
        int value = 100;
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            arrayList.add(value++);
        }
    }

}
