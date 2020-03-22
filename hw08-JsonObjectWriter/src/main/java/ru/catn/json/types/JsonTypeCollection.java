package ru.catn.json.types;

import java.util.Collection;

public class JsonTypeCollection implements JsonType {

    JsonType collection;

    public JsonTypeCollection() {
    }

    private JsonTypeCollection(Object object) {
        Object[] collectionElements = ((Collection) object).toArray();
        collection = JsonTypes.getType(collectionElements);
    }

    @Override
    public JsonType create(Object object) {
        return new JsonTypeCollection(object);
    }

    @Override
    public String write() {
        return collection.write();
    }

    @Override
    public boolean isMatched(Object object) {
        return Collection.class.isAssignableFrom(object.getClass());
    }
}
