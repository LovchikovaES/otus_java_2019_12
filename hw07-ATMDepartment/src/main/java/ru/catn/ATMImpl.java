package ru.catn;

import ru.catn.cell.Cell;
import ru.catn.cell.CellHistory;
import ru.catn.cell.CellImpl;
import ru.catn.cell.Note;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ATMImpl implements ATM, RestoreATMListener {

    private final List<Note> availableNotes;
    private TreeSet<CellImpl> cells = new TreeSet<>();
    private Map<Cell, CellHistory> cellsHistory = new HashMap<>();

    public ATMImpl(List<Note> availableNotes) {
        this.availableNotes = availableNotes;
        createCells();
    }

    @Override
    public Map<Note, Integer> putMoney(Map<Note, Integer> notes) throws Exception {
        Map<Note, Integer> incorrectNotes = new HashMap<>();

        for (Map.Entry<Note, Integer> note : notes.entrySet()) {
            if (note.getValue() < 0) {
                throw new RuntimeException("Quantity of notes must be positive");
            }
            if (note.getValue() == 0) {
                continue;
            }
            boolean correctNote = false;
            for (var cell : cells) {
                if (cell.getNote().equals(note.getKey())) {
                    cell.putNotes(note.getValue());
                    correctNote = true;
                    break;
                }
            }
            if (!correctNote) {
                incorrectNotes.put(note.getKey(), note.getValue());
            }
        }
        saveState();
        return incorrectNotes;
    }

    @Override
    public int getBalance() {
        int balance = 0;
        for (var cell : cells)
            balance = balance + cell.getBalance();
        return balance;
    }

    @Override
    public Map<Note, Integer> giveMoney(int amount) throws RuntimeException {
        if (amount <= 0) {
            throw new RuntimeException("Please request amount > 0");
        }
        if (amount > getBalance()) {
            throw new RuntimeException("Not enough money in ATM");
        }

        int restAmount = amount;
        Map<Note, Integer> notesToGive = new HashMap<>();
        Map<Cell, Integer> cellsWithNeedNotes = new HashMap<>();

        for (var cell : cells) {
            int quantityNotes = 0;
            while (restAmount >= cell.getDenomination()
                    && cell.getNotesQuantity() > quantityNotes) {
                quantityNotes++;
                restAmount = restAmount - cell.getDenomination();
            }
            if (quantityNotes > 0)
                cellsWithNeedNotes.put(cell, quantityNotes);
        }

        if (restAmount > 0) {
            throw new RuntimeException("Not enough notes for sum" + amount);
        }

        for (Map.Entry<Cell, Integer> cellWithNeedNotes : cellsWithNeedNotes.entrySet()) {
            cellWithNeedNotes.getKey().giveNotes(cellWithNeedNotes.getValue());
            notesToGive.put(cellWithNeedNotes.getKey().getNote(), cellWithNeedNotes.getValue());
        }

        return notesToGive;
    }

    private void createCells() {
        for (var availableNote : availableNotes) {
            CellImpl cell = new CellImpl(availableNote);
            cells.add(cell);
            cellsHistory.put(cell, new CellHistory());
        }
    }

    private void saveState() throws CloneNotSupportedException {
        for (var cell : cells) {
            CellHistory cellHistory = cellsHistory.get(cell);
            cellHistory.addState(cell.getState());
        }
    }

    @Override
    public void onRestoreATM() {
        for (var cell : cells) {
            CellHistory cellHistory = cellsHistory.get(cell);
            cell.restoreState(cellHistory.getFirstState());
        }
    }

    @Override
    public String toString() {
        return "ATM{" + cells +
                '}';
    }
}
