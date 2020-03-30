package ru.catn.atm.cell;

public class CellState implements Cloneable {
    private final int quantity;

    public CellState(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
