package ru.catn.json.types;

import java.util.Map;

public class JsonTypeMap implements JsonType {
    Map<Object, Object> entrySet;
    Class<?> typeKey;
    Class<?> typeValue;

    public JsonTypeMap() {
    }

    private JsonTypeMap(Object object) {
        this.entrySet = ((Map) object);
        Object[] values = ((Map) object).values().toArray();
        Object[] keys = ((Map) object).keySet().toArray();
        if (values.length > 0)
            this.typeValue = values[0].getClass();
        if (keys.length > 0)
            this.typeKey = keys[0].getClass();
    }

    @Override
    public JsonType create(Object object) {
        return new JsonTypeMap(object);
    }

    @Override
    public String write() {
        StringBuilder json = new StringBuilder();
        boolean isKeyNumber = new JsonTypeNumber().isNumberOrWrapper(typeKey);
        boolean isValueNumber = new JsonTypeNumber().isNumberOrWrapper(typeValue);

        json.append('{');
        for (Map.Entry<Object, Object> entry : entrySet.entrySet()) {
            if (!isKeyNumber)
                json.append('"');
            json.append(entry.getKey());
            if (!isKeyNumber)
                json.append('"');
            json.append(':');
            if (!isValueNumber)
                json.append('"');
            json.append(entry.getValue());
            if (!isValueNumber)
                json.append('"');
            json.append(',');
        }
        if (entrySet.size() > 0)
            json.deleteCharAt(json.length() - 1);
        json.append('}');
        return json.toString();
    }

    @Override
    public boolean isMatched(Object object) {
        return Map.class.isAssignableFrom(object.getClass());
    }
}
