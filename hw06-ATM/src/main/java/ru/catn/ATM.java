package ru.catn;

import java.util.*;

public class ATM implements CellManager {

    private CellManager cellManager;

    public ATM(List<Note> availableNotes) {
        cellManager = new CellManagerImpl(availableNotes);
    }

    @Override
    public int getBalance() {
        return cellManager.getBalance();
    }

    @Override
    public Map<Note,Integer> giveMoney(int amount) throws RuntimeException{
        return cellManager.giveMoney(amount);
    }

    @Override
    public Map<Note,Integer> putMoney(Map<Note,Integer> notes) {
        return cellManager.putMoney(notes);
    }
}
