package ru.catn.hibernate.core.service;

import ru.catn.hibernate.core.model.Address;

import java.util.Optional;

public interface DbServiceAddress {
    long saveAddress(Address address);

    Optional<Address> getAddress(long id);

}
