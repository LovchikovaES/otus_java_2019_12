package ru.catn.json.types;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonTypeObject implements JsonType {

    StringBuilder json = new StringBuilder();
    private Map<String, JsonType> boundFields = new LinkedHashMap<>();

    public JsonTypeObject() {
    }

    private JsonTypeObject(Map<String, JsonType> boundFields) {
        this.boundFields = boundFields;
    }

    @Override
    public JsonTypeObject create(Object object) {
        return new JsonTypeObject(getBoundFields(object));
    }

    @Override
    public String write() {
        json.append("{");
        for (Map.Entry<String, JsonType> boundField : boundFields.entrySet()) {
            writeName(boundField.getKey());
            json.append(boundField.getValue().write());
            json.append(',');
        }
        if (boundFields.size() > 0)
            json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }

    @Override
    public boolean isMatched(Object object) {
        return Object.class.isAssignableFrom(object.getClass());
    }

    private Map<String, JsonType> getBoundFields(Object object) {
        Map<String, JsonType> boundFields = new LinkedHashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (var field : fields) {
            if (Modifier.isTransient(field.getModifiers()))
                continue;

            if (field.trySetAccessible())
                field.setAccessible(true);
            else
                continue;

            JsonType fieldType;
            try {
                fieldType = JsonTypes.getType(field.get(object));
            } catch (IllegalAccessException e) {
                continue;
            }

            if (fieldType instanceof JsonTypeNull)
                continue;

            String fieldName = field.getName();
            boundFields.put(fieldName, fieldType);
        }
        return boundFields;
    }

    private void writeName(String name) {
        json.append('"')
                .append(name)
                .append('"')
                .append(':');
    }
}
