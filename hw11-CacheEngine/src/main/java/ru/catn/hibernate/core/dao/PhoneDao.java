package ru.catn.hibernate.core.dao;

import ru.catn.hibernate.core.model.Phone;
import ru.catn.hibernate.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> findById(long id);

    long savePhone(Phone phone);

    SessionManager getSessionManager();
}
