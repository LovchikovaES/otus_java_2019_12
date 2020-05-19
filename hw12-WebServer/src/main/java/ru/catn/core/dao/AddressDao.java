package ru.catn.core.dao;

import ru.catn.core.model.Address;
import ru.catn.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AddressDao {
    Optional<Address> findById(long id);

    long saveAddress(Address address);

    SessionManager getSessionManager();
}
