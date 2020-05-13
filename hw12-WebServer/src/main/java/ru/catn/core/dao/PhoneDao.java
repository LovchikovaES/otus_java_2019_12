package ru.catn.core.dao;

import ru.catn.core.model.Phone;
import ru.catn.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> findById(long id);

    long savePhone(Phone phone);

    SessionManager getSessionManager();
}
