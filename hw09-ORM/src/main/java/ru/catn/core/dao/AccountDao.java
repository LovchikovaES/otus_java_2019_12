package ru.catn.core.dao;

import ru.catn.core.model.Account;
import ru.catn.jdbc.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findById(long id);

    long saveAccount(Account account);

    boolean updateUser(Account account);

    SessionManager getSessionManager();
}
