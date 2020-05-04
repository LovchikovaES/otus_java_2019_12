package ru.catn.hibernate.core.dao;

import ru.catn.hibernate.core.model.Address;
import ru.catn.hibernate.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AddressDao {
    Optional<Address> findById(long id);

    long saveAddress(Address address);

    SessionManager getSessionManager();
}
