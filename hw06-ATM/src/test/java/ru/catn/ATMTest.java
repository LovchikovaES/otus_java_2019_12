package ru.catn;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ATMTest {
    ATM atm;
    Note noteOf50 = new Note(50);
    Note noteOf100 = new Note(100);
    Note noteOf500 = new Note(500);
    Note noteOf1000 = new Note(1000);

    @BeforeEach
    void setUp() {
        List<Note> availableNotes = Arrays.asList(noteOf50, noteOf100, noteOf500, noteOf1000);
        atm = new ATM(availableNotes);
    }

    @Test
    void checkBalance() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 2);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(noteOf50, 4);
        notesToPut.put(noteOf1000, 3);
        atm.putMoney(notesToPut); // 4600
        atm.giveMoney(2750);
        notesToPut.clear();
        notesToPut.put(noteOf100, 2);
        notesToPut.put(noteOf50, 3);
        atm.putMoney(notesToPut); // 350
        atm.giveMoney(850);
        Assertions.assertEquals(1350, atm.getBalance());
    }

    @Test
    void returnIncorrectNotes() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 2);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(new Note(30), 4);
        notesToPut.put(new Note(20), 3);

        Map<Note, Integer> incorrectNotes = new HashMap<>();
        incorrectNotes.put(new Note(30), 4);
        incorrectNotes.put(new Note(20), 3);

        Assertions.assertEquals(incorrectNotes, atm.putMoney(notesToPut));
    }

    @Test
    void checkBalanceWithIncorrectNotes() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 2);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(new Note(30), 4);
        notesToPut.put(new Note(20), 3);
        atm.putMoney(notesToPut);
        Assertions.assertEquals(1400, atm.getBalance());
    }

    @Test
    void returnNotEnoughMoney() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 1);
        notesToPut.put(noteOf100, 2);
        notesToPut.put(noteOf50, 3);
        notesToPut.put(noteOf1000, 1);
        atm.putMoney(notesToPut); // 1850
        Assertions.assertThrows(RuntimeException.class,
                () -> atm.giveMoney(1855),
                "Not enough money in ATM");
    }

    @Test
    void returnNotEnoughNotes() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 2);
        notesToPut.put(noteOf100, 2);
        notesToPut.put(noteOf50, 3);
        notesToPut.put(noteOf1000, 1);
        atm.putMoney(notesToPut); // 2250
        Assertions.assertThrows(RuntimeException.class,
                () -> atm.giveMoney(950),
                "Not enough notes for sum" + 950);
    }

    @Test
    void checkTwoReturnNotesForGive() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 4);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(noteOf50, 4);
        notesToPut.put(noteOf1000, 4);
        atm.putMoney(notesToPut); // 6600

        Map<Note,Integer> notesToGive = new HashMap<>();
        notesToGive.put(noteOf50, 1);
        notesToGive.put(noteOf1000, 2);

        Assertions.assertEquals(notesToGive, atm.giveMoney(2050));
    }

    @Test
    void checkFourReturnNotesForGive() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 4);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(noteOf50, 4);
        notesToPut.put(noteOf1000, 4);
        atm.putMoney(notesToPut); // 6600

        Map<Note,Integer> notesToGive = new HashMap<>();
        notesToGive.put(noteOf50, 1);
        notesToGive.put(noteOf1000, 4);
        notesToGive.put(noteOf500, 3);
        notesToGive.put(noteOf100, 2);

        Assertions.assertEquals(notesToGive, atm.giveMoney(5750));
    }

    @Test
    void checkGiveAllMoney() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 4);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(noteOf50, 4);
        notesToPut.put(noteOf1000, 4);
        atm.putMoney(notesToPut); // 6600

        Map<Note,Integer> notesToGive = new HashMap<>();
        notesToGive.put(noteOf1000, 4);
        notesToGive.put(noteOf500, 4);
        notesToGive.put(noteOf100, 4);
        notesToGive.put(noteOf50, 4);

        Assertions.assertEquals(notesToGive, atm.giveMoney(6600));
    }

    @Test
    void checkPutNegativeQuantitiesOfNotes() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, -3);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(noteOf50, 2);
        notesToPut.put(noteOf1000, 1);

        Assertions.assertThrows(RuntimeException.class,
                () -> atm.putMoney(notesToPut),"Quantity of notes must be positive");
    }

    @Test
    void checkPutZeroQuantitiesOfNotes() {
        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 0);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(noteOf50, 0);
        notesToPut.put(noteOf1000, 1);

        atm.putMoney(notesToPut); //1400
        Assertions.assertEquals(1400, atm.getBalance());
    }
}