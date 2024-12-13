package com.example.jkpvt.Core.SpringWebSecurity;

import com.google.gson.Gson;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Gson gson;
    private final ServletContext servletContext;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        try {
            if (request.getRequestURI().contains("api")) {
                // Set the response content type to JSON
                response.setContentType("application/json");

                int statusCode = HttpServletResponse.SC_UNAUTHORIZED;

                // Set the status code in the response
                response.setStatus(statusCode);

                // Create a response object with the error message
                ErrorResponse errorResponse = new ErrorResponse("Unauthorized access");

                // Convert the response object to JSON using Gson
                String jsonResponse = gson.toJson(errorResponse);

                // Write JSON response to the output stream
                try (PrintWriter writer = response.getWriter()) {
                    writer.write(jsonResponse);
                    writer.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Getter
    private static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
