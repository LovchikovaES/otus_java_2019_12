package ru.catn;

public interface Cell {
    int getNotesQuantity();
    int getBalance();
    int getDenomination();
    Note getNote();
    void giveNotes(int quantity) throws RuntimeException;
}
