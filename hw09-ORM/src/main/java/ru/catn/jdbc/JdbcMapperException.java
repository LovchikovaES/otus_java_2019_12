package ru.catn.jdbc;

public class JdbcMapperException extends RuntimeException {
    public JdbcMapperException(Exception ex) {
        super(ex);
    }
}
