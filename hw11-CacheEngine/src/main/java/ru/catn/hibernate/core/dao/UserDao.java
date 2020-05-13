package ru.catn.hibernate.core.dao;

import ru.catn.hibernate.core.model.User;
import ru.catn.hibernate.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long saveUser(User user);

    SessionManager getSessionManager();
}
