package ru.catn.atm;

import ru.catn.note.Note;

import java.util.Map;

public interface ATM {
    Map<Note, Integer> putMoney(Map<Note, Integer> notes) throws Exception;

    int getBalance();

    Map<Note, Integer> giveMoney(int amount) throws RuntimeException;
}
