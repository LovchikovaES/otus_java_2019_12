package ru.catn.json.types;

public class JsonTypeEnum implements JsonType {
    private Object value;

    public JsonTypeEnum() {
    }

    private JsonTypeEnum(Object object) {
        this.value = object;
    }

    @Override
    public JsonType create(Object object) {
        return new JsonTypeEnum(object);
    }

    @Override
    public String write() {
        StringBuilder json = new StringBuilder();
        json.append('"');
        json.append(value.toString());
        json.append('"');
        return json.toString();
    }

    @Override
    public boolean isMatched(Object object) {
        return object.getClass().isEnum();
    }
}
