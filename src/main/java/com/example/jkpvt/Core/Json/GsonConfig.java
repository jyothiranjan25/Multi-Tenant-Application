package com.example.jkpvt.Core.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

@Configuration
@EnableWebMvc
public class GsonConfig implements WebMvcConfigurer {

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
        gsonBuilder.registerTypeAdapter(Map.class, new TrimmedStringMapDeserializer());

        // Create and return the custom Gson instance
        return gsonBuilder.create();
    }

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
        gsonConverter.setGson(gson);
        return gsonConverter;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new QueryParamsArgumentResolver(gson()));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(gsonHttpMessageConverter(gson()));
    }
}
