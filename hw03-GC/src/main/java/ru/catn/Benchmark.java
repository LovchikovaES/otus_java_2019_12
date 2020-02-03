package ru.catn;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.*;

public class Benchmark implements BenchmarkMBean {

    private static final int INITIAL_CAPACITY = 1000; // 10
    private int size;
    private Integer newValue = 1;

    public Benchmark(int size) {
        this.size = size;
    }

    @Override
    public void run() {

        List<Integer> list = new ArrayList<>(INITIAL_CAPACITY);

        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < this.size; i++) {
            list.add(i, i);

            if (i >= 100) {
                // цикл по предудущим 100 элементам
                for (int j = i - 100; j <= i; j++) {
                    if (j % 3 == 0) {
                        list.set(j, newValue++); // если индекс кратен 3, то меняем значение
                    }
                }
            }
//            if ( i > 0 && ( (i + 1) % (100 * 100) == 0 ) ) {
//                System.out.println("processing time of " + 100 * 100 + " iterations: " + (System.currentTimeMillis() - beginTime) + "ms");
//                beginTime = System.currentTimeMillis();
//            }
        }
    }
}
