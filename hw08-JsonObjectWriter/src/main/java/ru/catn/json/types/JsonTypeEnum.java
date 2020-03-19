package ru.catn.json.types;

public class JsonTypeEnum implements JsonType {
    private Object value;

    public JsonTypeEnum() {
    }

    private JsonTypeEnum(Object value) {
        this.value = value;
    }

    @Override
    public JsonType create(Object object, Class<?> type) {
        if (type != null && type.isEnum())
            return new JsonTypeEnum(object);
        else
            return null;
    }

    @Override
    public String write() {
        StringBuilder json = new StringBuilder();
        json.append('"');
        json.append(value.toString());
        json.append('"');
        return json.toString();
    }
}
