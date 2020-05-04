package ru.catn.hibernate.core.service;

import ru.catn.hibernate.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

}
