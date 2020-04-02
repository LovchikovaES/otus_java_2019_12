package ru.catn.jdbc;

import java.util.Optional;

public interface JdbcMapper<T> {
    void create(T objectData);

    void update(T objectData);

    Optional<T> load(long id);
}
