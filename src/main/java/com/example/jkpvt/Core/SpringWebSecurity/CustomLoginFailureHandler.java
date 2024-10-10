package com.example.jkpvt.Core.SpringWebSecurity;

import com.example.jkpvt.Core.ExceptionHandling.RoleNotFoundExemption;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        String errorMessage;
        if (exception instanceof DisabledException) {
            errorMessage = "This account is disabled, please contact the administrator";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "User not found";
        } else if (exception instanceof RoleNotFoundExemption) {
            errorMessage = "Role not found";
        } else {
            errorMessage = "Invalid username or password";
        }
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
