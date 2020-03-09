package ru.catn.cell;

import java.util.ArrayDeque;
import java.util.Deque;

public class CellHistory {
    private Deque<CellState> states = new ArrayDeque<>();

    public void addState(CellState state) {
        states.add(state);
    }

    public CellState getFirstState() {
        if (states.size() == 0) {
            throw new RuntimeException("No first state");
        }
        return states.getFirst();
    }
}
