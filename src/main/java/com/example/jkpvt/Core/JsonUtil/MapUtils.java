package com.example.jkpvt.Core.JsonUtil;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapUtils {

    private static Gson gson;

    @Autowired
    public MapUtils(Gson gson) {
        MapUtils.gson = gson;
    }

    public static <T> T toDto(Map<String, String> map, Class<T> clazz) {
        map.replaceAll((key, value) -> value.isEmpty() ? null : value);
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }
}
