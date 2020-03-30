package ru.catn.json.types;

public class JsonTypeNull implements JsonType {
    public JsonTypeNull() {
    }

    @Override
    public JsonType create(Object object) {
        return new JsonTypeNull();
    }

    @Override
    public String write() {
        return "null";
    }

    @Override
    public boolean isMatched(Object object) {
        return object == null;
    }
}
