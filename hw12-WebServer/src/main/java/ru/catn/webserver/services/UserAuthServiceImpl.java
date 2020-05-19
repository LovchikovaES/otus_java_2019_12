package ru.catn.webserver.services;

import ru.catn.core.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceUser.getByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
