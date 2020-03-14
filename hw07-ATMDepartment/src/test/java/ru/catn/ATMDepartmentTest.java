package ru.catn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.catn.cell.Note;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ATMDepartmentTest {

    ATMDepartment atmDepartment = new ATMDepartment();
    Note noteOf50 = new Note(50);
    Note noteOf100 = new Note(100);
    Note noteOf500 = new Note(500);
    Note noteOf1000 = new Note(1000);
    List<Note> availableNotes = Arrays.asList(noteOf50, noteOf100, noteOf500, noteOf1000);

    @Test
    void checkBalance() throws Exception {
        List<ATM> atms = Arrays.asList(new ATMImpl(availableNotes), new ATMImpl(availableNotes), new ATMImpl(availableNotes));
        atmDepartment.addATMs(atms);

        int i = 0;
        for (var atm : atms) {
            Map<Note, Integer> notesToPut = new HashMap<>();
            switch (i) {
                case 0:
                    notesToPut.put(noteOf500, 2);
                    break;
                case 1:
                    notesToPut.put(noteOf100, 2);
                    break;
                case 2:
                    notesToPut.put(noteOf1000, 2);
                    break;
            }
            atm.putMoney(notesToPut);
            i++;
        }

        Assertions.assertEquals(3200, atmDepartment.getBalance());
    }

    @Test
    void checkRestoreToFirstState() throws Exception {
        List<ATM> atms = Arrays.asList(new ATMImpl(availableNotes), new ATMImpl(availableNotes), new ATMImpl(availableNotes));
        atmDepartment.addATMs(atms);

        int i = 0;
        for (var atm : atms) {
            Map<Note, Integer> notesToPut = new HashMap<>();
            switch (i) {
                case 0:
                    notesToPut.put(noteOf500, 1);
                    break;
                case 1:
                    notesToPut.put(noteOf100, 1);
                    break;
                case 2:
                    notesToPut.put(noteOf1000, 1);
                    break;
            }
            atm.putMoney(notesToPut); // 1600
            i++;
        }

        i = 0;
        for(var atm : atms) {
            Map<Note, Integer> notesToPut = new HashMap<>();
            switch (i) {
                case 0:
                    notesToPut.put(noteOf50, 2);
                    break;
                case 1:
                    notesToPut.put(noteOf100, 2);
                    break;
                case 2:
                    notesToPut.put(noteOf1000, 2);
                    break;
            }
            atm.putMoney(notesToPut); // 2300
            i++;
        }
        atmDepartment.restoreATMs();
        Assertions.assertEquals(1600, atmDepartment.getBalance());
    }
}