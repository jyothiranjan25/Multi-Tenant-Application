package com.example.jkpvt.Core.Yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlConfig {

    private static final Yaml YAML = new Yaml();

    public static String getValueForKey(String key, String filePath) {
        try (InputStream inputStream = YamlConfig.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + filePath);
            }
            Map<String, Object> yamlMap = YAML.load(inputStream);
            return getValue(yamlMap, key);
        } catch (IOException e) {
            return key;
        }
    }

    private static String getValue(Map<String, Object> yamlMap, String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = yamlMap;

        for (int i = 0; i < keys.length - 1; i++) {
            Object value = currentMap.get(keys[i]);
            if (value instanceof Map) {
                currentMap = (Map<String, Object>) value;
            } else {
                return key;
            }
        }

        Object finalValue = currentMap.get(keys[keys.length - 1]);
        return finalValue instanceof String ? (String) finalValue : key;
    }
}