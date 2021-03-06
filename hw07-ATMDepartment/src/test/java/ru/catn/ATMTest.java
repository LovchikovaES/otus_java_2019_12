package ru.catn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.catn.atm.ATMImpl;
import ru.catn.note.Note;
import ru.catn.note.NoteFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class ATMTest {
    ATMImpl atm;
    NoteFactory noteFactory = new NoteFactory(Arrays.asList(
            new Note(50),
            new Note(100),
            new Note(500),
            new Note(1000)));

    @BeforeEach
    void setUp() {
        atm = new ATMImpl(noteFactory);
    }

    @Test
    void checkBalance() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 2);
        notesToPut.put(noteFactory.getNote(100), 4);
        notesToPut.put(noteFactory.getNote(50), 4);
        notesToPut.put(noteFactory.getNote(1000), 3);
        atm.putMoney(notesToPut); // 4600
        atm.giveMoney(2750);
        notesToPut.clear();
        notesToPut.put(noteFactory.getNote(100), 2);
        notesToPut.put(noteFactory.getNote(50), 3);
        atm.putMoney(notesToPut); // 350
        atm.giveMoney(850);
        Assertions.assertEquals(1350, atm.getBalance());
    }

    @Test
    void returnIncorrectNotes() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 2);
        notesToPut.put(noteFactory.getNote(100), 4);
        notesToPut.put(new Note(30), 4);
        notesToPut.put(new Note(20), 3);

        Map<Note, Integer> incorrectNotes = new HashMap<>();
        incorrectNotes.put(new Note(30), 4);
        incorrectNotes.put(new Note(20), 3);

        Assertions.assertEquals(incorrectNotes, atm.putMoney(notesToPut));
    }

    @Test
    void checkBalanceWithIncorrectNotes() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 2);
        notesToPut.put(noteFactory.getNote(100), 4);
        notesToPut.put(new Note(30), 4);
        notesToPut.put(new Note(20), 3);
        atm.putMoney(notesToPut);
        Assertions.assertEquals(1400, atm.getBalance());
    }

    @Test
    void returnNotEnoughMoney() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 1);
        notesToPut.put(noteFactory.getNote(100), 2);
        notesToPut.put(noteFactory.getNote(50), 3);
        notesToPut.put(noteFactory.getNote(1000), 1);
        atm.putMoney(notesToPut); // 1850
        Assertions.assertThrows(RuntimeException.class,
                () -> atm.giveMoney(1855),
                "Not enough money in ATM");
    }

    @Test
    void returnNotEnoughNotes() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 2);
        notesToPut.put(noteFactory.getNote(100), 2);
        notesToPut.put(noteFactory.getNote(50), 3);
        notesToPut.put(noteFactory.getNote(1000), 1);
        atm.putMoney(notesToPut); // 2250
        Assertions.assertThrows(RuntimeException.class,
                () -> atm.giveMoney(950),
                "Not enough notes for sum" + 950);
    }

    @Test
    void checkTwoReturnNotesForGive() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(1000), 4);
        notesToPut.put(noteFactory.getNote(500), 4);
        notesToPut.put(noteFactory.getNote(100), 4);
        notesToPut.put(noteFactory.getNote(50), 4);
        notesToPut.put(noteFactory.getNote(1000), 4);
        atm.putMoney(notesToPut); // 6600

        Map<Note, Integer> notesToGive = new HashMap<>();
        notesToGive.put(noteFactory.getNote(50), 1);
        notesToGive.put(noteFactory.getNote(1000), 2);

        Assertions.assertEquals(notesToGive, atm.giveMoney(2050));
    }

    @Test
    void checkFourReturnNotesForGive() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 4);
        notesToPut.put(noteFactory.getNote(100), 4);
        notesToPut.put(noteFactory.getNote(50), 4);
        notesToPut.put(noteFactory.getNote(1000), 4);
        atm.putMoney(notesToPut); // 6600

        Map<Note, Integer> notesToGive = new HashMap<>();
        notesToGive.put(noteFactory.getNote(50), 1);
        notesToGive.put(noteFactory.getNote(1000), 4);
        notesToGive.put(noteFactory.getNote(500), 3);
        notesToGive.put(noteFactory.getNote(100), 2);

        Assertions.assertEquals(notesToGive, atm.giveMoney(5750));
    }

    @Test
    void checkGiveAllMoney() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 4);
        notesToPut.put(noteFactory.getNote(100), 4);
        notesToPut.put(noteFactory.getNote(50), 4);
        notesToPut.put(noteFactory.getNote(1000), 4);
        atm.putMoney(notesToPut); // 6600

        Map<Note, Integer> notesToGive = new HashMap<>();
        notesToGive.put(noteFactory.getNote(1000), 4);
        notesToGive.put(noteFactory.getNote(500), 4);
        notesToGive.put(noteFactory.getNote(100), 4);
        notesToGive.put(noteFactory.getNote(50), 4);

        Assertions.assertEquals(notesToGive, atm.giveMoney(6600));
    }

    @Test
    void checkPutNegativeQuantitiesOfNotes() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), -3);
        notesToPut.put(noteFactory.getNote(100), 4);
        notesToPut.put(noteFactory.getNote(50), 2);
        notesToPut.put(noteFactory.getNote(1000), 1);

        Assertions.assertThrows(RuntimeException.class,
                () -> atm.putMoney(notesToPut), "Quantity of notes must be positive");
    }

    @Test
    void checkPutZeroQuantitiesOfNotes() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 0);
        notesToPut.put(noteFactory.getNote(100), 4);
        notesToPut.put(noteFactory.getNote(50), 0);
        notesToPut.put(noteFactory.getNote(1000), 1);

        atm.putMoney(notesToPut); //1400
        Assertions.assertEquals(1400, atm.getBalance());
    }

    @Test
    void checkBalanceForTwoTimesToPut() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 2);
        notesToPut.put(noteFactory.getNote(100), 3);
        notesToPut.put(noteFactory.getNote(50), 4);
        notesToPut.put(noteFactory.getNote(1000), 5);

        atm.putMoney(notesToPut); // 6500

        notesToPut.clear();
        notesToPut.put(noteFactory.getNote(500), 1);
        notesToPut.put(noteFactory.getNote(100), 1);
        notesToPut.put(noteFactory.getNote(50), 1);
        notesToPut.put(noteFactory.getNote(1000), 1);

        atm.putMoney(notesToPut); // 1650

        Assertions.assertEquals(8150, atm.getBalance());
    }

    @Test
    void checkRestoreATMtoInitialStateForAllNotes() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 2);
        notesToPut.put(noteFactory.getNote(100), 3);
        notesToPut.put(noteFactory.getNote(50), 4);
        notesToPut.put(noteFactory.getNote(1000), 5);

        atm.putMoney(notesToPut); // 6500

        notesToPut.clear();
        notesToPut.put(noteFactory.getNote(500), 1);
        notesToPut.put(noteFactory.getNote(100), 1);
        notesToPut.put(noteFactory.getNote(50), 1);
        notesToPut.put(noteFactory.getNote(1000), 1);

        atm.putMoney(notesToPut); // 1650

        atm.onRestoreATM();
        Assertions.assertEquals(6500, atm.getBalance());
    }

    @Test
    void checkRestoreATMtoInitialStateWithNoInitialState() {
        Assertions.assertThrows(RuntimeException.class,
                () -> atm.onRestoreATM(), "No first state");
    }

    @Test
    void checkRestoreATMtoInitialState() throws Exception {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteFactory.getNote(500), 2);
        notesToPut.put(noteFactory.getNote(1000), 5);
        atm.putMoney(notesToPut); // 6000

        notesToPut.clear();
        notesToPut.put(noteFactory.getNote(100), 1);
        notesToPut.put(noteFactory.getNote(50), 1);

        atm.putMoney(notesToPut); // 150

        atm.onRestoreATM();
        Assertions.assertEquals(6000, atm.getBalance());
    }
}