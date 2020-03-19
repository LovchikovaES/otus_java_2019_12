package ru.catn.json.types;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JsonTypeString implements JsonType {
    private final Set<Class<?>> stringWrappers = new HashSet<>(Arrays.asList(
            Character.class,
            String.class
    ));
    private Object value;

    public JsonTypeString() {
    }

    private JsonTypeString(Object value) {
        this.value = value;
    }

    @Override
    public JsonType create(Object object, Class<?> type) {
        return isNumberOrWrapper(type) ? new JsonTypeString(object) : null;
    }

    @Override
    public String write() {
        StringBuilder json = new StringBuilder();
        json.append('"');
        json.append(value.toString());
        json.append('"');
        return json.toString();
    }

    private boolean isNumberOrWrapper(Class<?> type) {
        if (type == null)
            return false;
        else
            return type.getSimpleName().equals("char") || stringWrappers.contains(type);
    }
}
