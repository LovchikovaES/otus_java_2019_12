package ru.catn.json;

import ru.catn.json.types.JsonType;
import ru.catn.json.types.JsonTypes;

public class JsonObjectWriter {

    public String toJson(Object object) {
        StringBuilder json = new StringBuilder();
        JsonType jsonType = JsonTypes.getType(object);
        json.append(jsonType.write());
        return json.toString();
    }

}
