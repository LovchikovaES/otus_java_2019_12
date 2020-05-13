package ru.catn.hibernate.core.service;

import ru.catn.hibernate.core.model.Phone;

import java.util.Optional;

public interface DbServicePhone {
    long savePhone(Phone phone);

    Optional<Phone> getPhone(long id);
}
