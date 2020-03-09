package ru.catn;

import java.util.ArrayList;
import java.util.List;

public class RestoreATMProducer {
    private final List<RestoreATMListener> listeners = new ArrayList<>();

    void addListener(RestoreATMListener listener) {
        listeners.add(listener);
    }

    void event() {
        listeners.forEach(listener -> listener.onRestoreATM());
    }
}
