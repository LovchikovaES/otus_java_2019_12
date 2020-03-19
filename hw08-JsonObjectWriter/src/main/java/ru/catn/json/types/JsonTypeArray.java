package ru.catn.json.types;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JsonTypeArray implements JsonType {
    private List<JsonType> listValues = new ArrayList<>();

    public JsonTypeArray() {
    }

    private JsonTypeArray(Object value, Class<?> type) {
        for (int i = 0; i < Array.getLength(value); i++) {
            listValues.add(JsonTypes.getType(Array.get(value, i), type.getComponentType()));
        }
    }

    @Override
    public JsonType create(Object object, Class<?> type) {
        if (type != null && type.isArray())
            return new JsonTypeArray(object, type);
        else
            return null;
    }

    @Override
    public String write() {
        StringBuilder json = new StringBuilder();
        json.append('[');
        for (var value : listValues) {
            json.append(value.write());
            json.append(',');
        }
        if (listValues.size() > 0) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append(']');
        return json.toString();
    }
}
