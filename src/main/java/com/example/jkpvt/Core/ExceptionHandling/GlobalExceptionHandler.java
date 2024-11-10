package com.example.jkpvt.Core.ExceptionHandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        Map<String, String> response = Map.of("message", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
