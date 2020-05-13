package ru.catn.core.service;

import ru.catn.core.model.Phone;

import java.util.Optional;

public interface DbServicePhone {
    long savePhone(Phone phone);

    Optional<Phone> getPhone(long id);
}
