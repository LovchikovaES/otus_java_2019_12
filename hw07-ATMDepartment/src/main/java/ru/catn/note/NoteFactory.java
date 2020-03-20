package ru.catn.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteFactory {
    private final Map<Integer, Note> notes;

    public NoteFactory(List<Note> availableNotes) {
        Map<Integer, Note> notes = new HashMap<>();
        for (var availableNote : availableNotes) {
            notes.put(availableNote.getDenomination(), availableNote);
        }
        this.notes = notes;
    }

    public Note getNote(int denomination) {
        return notes.get(denomination);
    }

    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<>();
        for (Map.Entry<Integer, Note> note : notes.entrySet()) {
            noteList.add(note.getValue());
        }
        return noteList;
    }
}
