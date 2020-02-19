package ru.catn;

import java.util.Map;

public interface CellManager {
    Map<Note, Integer> putMoney(Map<Note, Integer> notes);
    int getBalance();
    Map<Note, Integer> giveMoney(int amount) throws RuntimeException;
}
