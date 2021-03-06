package ru.catn.atm.cell;

import ru.catn.note.Note;

public class CellImpl implements Cell, Comparable<CellImpl>, Restorable<CellState> {

    private final Note note;
    private int quantity;

    public CellImpl(Note note) {
        this.note = note;
    }

    @Override
    public CellState getState() {
        return new CellState(this.quantity);
    }

    @Override
    public void restoreState(CellState state) {
        this.quantity = state.getQuantity();
    }

    @Override
    public int getNotesQuantity() {
        return this.quantity;
    }

    @Override
    public int getDenomination() {
        return this.note.getDenomination();
    }

    @Override
    public Note getNote() {
        return this.note;
    }

    @Override
    public int getBalance() {
        return getNotesQuantity() * getDenomination();
    }

    @Override
    public int compareTo(CellImpl o) {
        return o.note.getDenomination() - this.note.getDenomination();
    }

    @Override
    public void giveNotes(int quantity) throws RuntimeException {
        if (quantity > this.quantity) {
            throw new RuntimeException("Not enough notes of denomination" + getDenomination());
        }
        this.quantity = this.quantity - quantity;
    }

    @Override
    public void putNotes(int quantity) {
        this.quantity = this.quantity + quantity;
    }

    @Override
    public String toString() {
        return "{" + note + ' ' + quantity +
                '}';
    }
}
