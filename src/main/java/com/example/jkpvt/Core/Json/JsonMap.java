package com.example.jkpvt.Core.Json;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonMap {

    private static Gson gson;

    @Autowired
    public JsonMap(Gson gson) {
        JsonMap.gson = gson;
    }

    public static <T> T toDto(Map<String, String> map, Class<T> clazz) {
        map.replaceAll((key, value) -> value.isEmpty() ? null : value);
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }
}
