package com.example.jkpvt.Core.Json;

import com.example.jkpvt.Core.Json.TypeAdapter.LocalDateTimeAdapter;
import com.example.jkpvt.Core.Json.TypeAdapter.MapStringDeserializer;
import com.example.jkpvt.Core.Json.TypeAdapter.StringTrimmerDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Map;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

@Configuration
public class GsonConfig {

    @Bean
    public Gson gson() {
        // Create a GsonBuilder to configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Configure Gson here
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES);

        // Register custom TypeAdapter for LocalDateTime
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        // Register the custom deserializer for Map<String, String> to trim all values
        gsonBuilder.registerTypeAdapter(Map.class, new MapStringDeserializer());
        // Register the custom deserializer for String to trim all values
        gsonBuilder.registerTypeAdapter(String.class, new StringTrimmerDeserializer());

        // Create and return the custom Gson instance
        return gsonBuilder.create();
    }
}
