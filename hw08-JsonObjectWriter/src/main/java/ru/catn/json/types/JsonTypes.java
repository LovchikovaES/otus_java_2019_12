package ru.catn.json.types;

import java.util.Arrays;
import java.util.List;

public class JsonTypes {
    public static List<JsonType> jsonTypes =
            Arrays.asList(
                    new JsonTypeNull(),
                    new JsonTypeEnum(),
                    new JsonTypeArray(),
                    new JsonTypeString(),
                    new JsonTypeNumber(),
                    new JsonTypeCollection(),
                    new JsonTypeMap(),
                    new JsonTypeObject()
            );

    public static JsonType getType(Object object) {
        return object == null ? new JsonTypeNull() : getType(object, object.getClass());
    }

    public static JsonType getType(Object object, Class<?> type) {
        JsonType jsonType = null;

        for (var jType : jsonTypes) {
            jsonType = jType.create(object, type);
            if (jsonType != null) {
                break;
            }
        }
        return jsonType;
    }

}
