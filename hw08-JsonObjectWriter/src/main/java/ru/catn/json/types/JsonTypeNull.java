package ru.catn.json.types;

public class JsonTypeNull implements JsonType {
    public JsonTypeNull() {
    }

    @Override
    public JsonType create(Object object, Class<?> type) {
        return object == null ? new JsonTypeNull() : null;
    }

    @Override
    public String write() {
        return "null";
    }
}
