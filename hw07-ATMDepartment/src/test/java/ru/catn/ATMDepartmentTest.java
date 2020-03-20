package ru.catn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.catn.atm.ATM;
import ru.catn.atm.ATMDepartment;
import ru.catn.atm.ATMImpl;
import ru.catn.note.Note;
import ru.catn.note.NoteFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ATMDepartmentTest {

    ATMDepartment atmDepartment = new ATMDepartment();
    NoteFactory noteFactory = new NoteFactory(Arrays.asList(
            new Note(50),
            new Note(100),
            new Note(500),
            new Note(1000)));

    @Test
    void checkBalance() throws Exception {
        List<ATM> atms = Arrays.asList(new ATMImpl(noteFactory), new ATMImpl(noteFactory), new ATMImpl(noteFactory));
        atmDepartment.addATMs(atms);

        int i = 0;
        for (var atm : atms) {
            Map<Note, Integer> notesToPut = new HashMap<>();
            switch (i) {
                case 0:
                    notesToPut.put(noteFactory.getNote(500), 2);
                    break;
                case 1:
                    notesToPut.put(noteFactory.getNote(100), 2);
                    break;
                case 2:
                    notesToPut.put(noteFactory.getNote(1000), 2);
                    break;
            }
            atm.putMoney(notesToPut);
            i++;
        }

        Assertions.assertEquals(3200, atmDepartment.getBalance());
    }

    @Test
    void checkRestoreToFirstState() throws Exception {
        List<ATM> atms = Arrays.asList(new ATMImpl(noteFactory), new ATMImpl(noteFactory), new ATMImpl(noteFactory));
        atmDepartment.addATMs(atms);

        int i = 0;
        for (var atm : atms) {
            Map<Note, Integer> notesToPut = new HashMap<>();
            switch (i) {
                case 0:
                    notesToPut.put(noteFactory.getNote(500), 1);
                    break;
                case 1:
                    notesToPut.put(noteFactory.getNote(100), 1);
                    break;
                case 2:
                    notesToPut.put(noteFactory.getNote(1000), 1);
                    break;
            }
            atm.putMoney(notesToPut); // 1600
            i++;
        }

        i = 0;
        for (var atm : atms) {
            Map<Note, Integer> notesToPut = new HashMap<>();
            switch (i) {
                case 0:
                    notesToPut.put(noteFactory.getNote(50), 2);
                    break;
                case 1:
                    notesToPut.put(noteFactory.getNote(100), 2);
                    break;
                case 2:
                    notesToPut.put(noteFactory.getNote(1000), 2);
                    break;
            }
            atm.putMoney(notesToPut); // 2300
            i++;
        }
        atmDepartment.restoreATMs();
        Assertions.assertEquals(1600, atmDepartment.getBalance());
    }
}