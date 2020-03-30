package ru.catn.atm.cell;

import ru.catn.note.Note;

public interface Cell {
    int getNotesQuantity();

    int getBalance();

    int getDenomination();

    Note getNote();

    void giveNotes(int quantity) throws RuntimeException;

    void putNotes(int quantity);
}
