package com.example.jkpvt.Core.Messages;

import com.example.jkpvt.Core.Yaml.YamlConfig;

public class ApplicationText {
    public static String get(String key) {
        String fileName = "/ApplicationTexts/ApplicationText.yaml";
        return YamlConfig.getValueForKey(key, fileName);
    }
}
