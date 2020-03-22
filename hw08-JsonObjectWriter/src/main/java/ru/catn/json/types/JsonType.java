package ru.catn.json.types;

public interface JsonType {
    boolean isMatched(Object object);

    JsonType create(Object object);

    String write();
}
