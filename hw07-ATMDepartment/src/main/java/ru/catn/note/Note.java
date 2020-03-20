package ru.catn.note;

import java.util.Objects;

public class Note {
    private final int denomination;

    public Note(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return this.denomination;
    }

    @Override
    public String toString() {
        return "Note" + denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return denomination == note.denomination;
    }


    @Override
    public int hashCode() {
        return Objects.hash(denomination);
    }
}
