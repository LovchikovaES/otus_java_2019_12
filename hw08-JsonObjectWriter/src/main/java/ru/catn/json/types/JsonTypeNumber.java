package ru.catn.json.types;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JsonTypeNumber implements JsonType {

    private final Set<Class<?>> numberWrappers = new HashSet<>(Arrays.asList(Boolean.class,
            Byte.class, Short.class, Integer.class,
            Long.class, Float.class, Double.class));

    private Object value;

    public JsonTypeNumber() {
    }

    private JsonTypeNumber(Object value) {
        this.value = value;
    }

    @Override
    public JsonType create(Object object, Class<?> type) {
        if (isNumberOrWrapper(type)) {
            return new JsonTypeNumber(object);
        } else {
            return null;
        }
    }

    @Override
    public String write() {
        return value.toString();
    }

    public boolean isNumberOrWrapper(Class<?> type) {
        if (type == null) {
            return false;
        } else {
            return type.isPrimitive() || numberWrappers.contains(type);
        }
    }
}
