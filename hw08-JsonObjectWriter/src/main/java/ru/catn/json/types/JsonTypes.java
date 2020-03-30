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
        for (var jsonType : jsonTypes) {
            if (jsonType.isMatched(object))
                return jsonType.create(object);
        }
        return new JsonTypeNull();
    }

}
