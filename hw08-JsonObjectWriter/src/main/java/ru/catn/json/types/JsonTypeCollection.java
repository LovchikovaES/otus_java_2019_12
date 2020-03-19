package ru.catn.json.types;

import java.util.Collection;

public class JsonTypeCollection implements JsonType {

    JsonType collection;

    public JsonTypeCollection() {
    }

    private JsonTypeCollection(Object object) {
        Object[] collectionElements = ((Collection) object).toArray();
        collection = JsonTypes.getType(collectionElements, collectionElements.getClass());
    }

    @Override
    public JsonType create(Object object, Class<?> type) {
        if (!Collection.class.isAssignableFrom(object.getClass()))
            return null;
        else
            return new JsonTypeCollection(object);
    }

    @Override
    public String write() {
        return collection.write();
    }
}
