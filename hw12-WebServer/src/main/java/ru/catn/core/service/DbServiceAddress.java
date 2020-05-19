package ru.catn.core.service;

import ru.catn.core.model.Address;

import java.util.Optional;

public interface DbServiceAddress {
    long saveAddress(Address address);

    Optional<Address> getAddress(long id);

}
