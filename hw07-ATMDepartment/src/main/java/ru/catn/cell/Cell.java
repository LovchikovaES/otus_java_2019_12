package ru.catn.cell;

public interface Cell {
    int getNotesQuantity();

    int getBalance();

    int getDenomination();

    Note getNote();

    void giveNotes(int quantity) throws RuntimeException;

    void putNotes(int quantity);
}
