package ru.catn.core.service;

import ru.catn.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    boolean updateUser(User user);
}
