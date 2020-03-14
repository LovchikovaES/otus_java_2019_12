package ru.catn.cell;

import java.util.ArrayDeque;
import java.util.Deque;

public class CellHistory {
    private Deque<CellState> states = new ArrayDeque<>();

    public void addState(CellState state) throws CloneNotSupportedException {
        states.add((CellState) state.clone());
    }

    public CellState getFirstState() {
        if (states.size() == 0) {
            throw new RuntimeException("No first state");
        }
        return states.getFirst();
    }
}
