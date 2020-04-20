package ru.catn.core.service;

import ru.catn.core.model.Account;

import java.util.Optional;

public interface DbServiceAccount {
    long saveAccount(Account account);

    Optional<Account> getAccount(long id);

    boolean updateAccount(Account account);
}
