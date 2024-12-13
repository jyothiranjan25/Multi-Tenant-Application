package com.example.jkpvt.Core.Json.TypeAdapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MapStringDeserializer implements JsonDeserializer<Map<String, String>> {
    @Override
    public Map<String, String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<String, String> result = new HashMap<>();
        JsonObject jsonObject = json.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String value = entry.getValue().getAsString().trim(); // Trim the value
            result.put(entry.getKey(), value);
        }

        return result;
    }
}
