package ru.catn.atm.cell;

public interface Restorable<S> {
    S getState();

    void restoreState(S state);
}
