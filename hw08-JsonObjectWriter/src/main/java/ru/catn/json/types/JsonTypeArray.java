package ru.catn.json.types;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JsonTypeArray implements JsonType {
    private List<JsonType> listValues = new ArrayList<>();

    public JsonTypeArray() {
    }

    private JsonTypeArray(Object object) {
        for (int i = 0; i < Array.getLength(object); i++) {
            listValues.add(JsonTypes.getType(Array.get(object, i)));
        }
    }

    @Override
    public JsonType create(Object object) {
        return new JsonTypeArray(object);
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

    @Override
    public boolean isMatched(Object object) {
        return object.getClass().isArray();
    }

}
