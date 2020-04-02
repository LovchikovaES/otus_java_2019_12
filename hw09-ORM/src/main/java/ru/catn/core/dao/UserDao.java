package ru.catn.core.dao;

import ru.catn.core.model.User;
import ru.catn.jdbc.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long saveUser(User user);

    boolean updateUser(User user);

    SessionManager getSessionManager();
}
