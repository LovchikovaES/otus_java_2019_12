package ru.catn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {

        Note noteOf50 = new Note(50);
        Note noteOf100 = new Note(100);
        Note noteOf500 = new Note(500);
        Note noteOf1000 = new Note(1000);

        List<Note> availableNotes = Arrays.asList(noteOf50, noteOf100, noteOf500, noteOf1000);
        CellManager atm = new CellManagerImpl(availableNotes);

        Map<Note, Integer> notesToPut = new HashMap<>();
        notesToPut.put(noteOf500, 13);
        notesToPut.put(noteOf100, 4);
        notesToPut.put(noteOf50, 3);
        notesToPut.put(new Note(30), 3);

        System.out.println("Notes to put: " + notesToPut);
        System.out.println("Incorrect notes: " + atm.putMoney(notesToPut));
        System.out.println("Balance: " + atm.getBalance());
        System.out.println("Notes for 2750: " + atm.giveMoney(2750));
        System.out.println("Balance: " + atm.getBalance());
    }
}
