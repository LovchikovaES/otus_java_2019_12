package ru.catn.json.types;

public interface JsonType {
    JsonType create(Object object, Class<?> type);

    String write();
}
