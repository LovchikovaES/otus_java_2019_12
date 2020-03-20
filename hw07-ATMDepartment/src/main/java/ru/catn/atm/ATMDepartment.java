package ru.catn.atm;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {
    private List<ATM> ATMList = new ArrayList<>();
    private RestoreATMProducer restoreATMProducer = new RestoreATMProducer();

    public void addATM(ATM atm) {
        ATMList.add(atm);
        restoreATMProducer.addListener((RestoreATMListener) atm);
    }

    public void addATMs(List<ATM> ATMList) {
        for (var atm : ATMList) {
            addATM(atm);
        }
    }

    public int getBalance() {
        int balance = 0;
        for (var ATM : ATMList) {
            balance = balance + ATM.getBalance();
        }
        return balance;
    }

    public void restoreATMs() {
        restoreATMProducer.processEvent();
    }

    @Override
    public String toString() {
        return "ATMDepartment{" + ATMList +
                '}';
    }
}
