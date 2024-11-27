package com.example.jkpvt.Core.Json;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QueryParamsArgumentResolver implements HandlerMethodArgumentResolver {

    private final Gson gson;

    @Autowired
    public QueryParamsArgumentResolver(Gson gson) {
        this.gson = gson;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(QueryParams.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> dtoClass = parameter.getParameterType();

        // Get parameters from URL query
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        Map<String, String> singleValueMap = parameterMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

        // Get parameters from request body
        String body = webRequest.getNativeRequest(HttpServletRequest.class).getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        Map<String, String> bodyMap = gson.fromJson(body, Map.class);

        // Merge both maps
        if(bodyMap != null)
            singleValueMap.putAll(bodyMap);

        String json = gson.toJson(singleValueMap);
        return gson.fromJson(json, dtoClass);
    }
}
