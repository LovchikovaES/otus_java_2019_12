package ru.catn.webserver.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
