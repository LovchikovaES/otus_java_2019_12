package ru.catn.jdbc.dao;

public class AccountDaoException extends RuntimeException {
    public AccountDaoException(Exception ex) {
        super(ex);
    }
}
