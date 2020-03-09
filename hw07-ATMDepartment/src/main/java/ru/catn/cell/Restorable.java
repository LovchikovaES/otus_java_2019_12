package ru.catn.cell;

public interface Restorable<S> {
    S getState();

    void restoreState(S state);
}
