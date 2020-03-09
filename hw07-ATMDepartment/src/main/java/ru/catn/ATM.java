package ru.catn;

import ru.catn.cell.Note;

import java.util.Map;

public interface ATM {
    Map<Note, Integer> putMoney(Map<Note, Integer> notes) throws RuntimeException;

    int getBalance();

    Map<Note, Integer> giveMoney(int amount) throws RuntimeException;
}