package com.example.jkpvt.Core.WebSecurity;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final Gson gson;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorMessage = "Invalid username or password";

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(gson.toJson(errorResponse));
            writer.flush();
        }
    }

    @Getter
    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

    }

}
