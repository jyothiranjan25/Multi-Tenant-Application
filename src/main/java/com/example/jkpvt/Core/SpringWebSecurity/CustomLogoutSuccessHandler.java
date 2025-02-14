package com.example.jkpvt.Core.SpringWebSecurity;

import com.example.jkpvt.Entities.UserManagement.UserLogin.UserLoginDetails;
import com.example.jkpvt.Entities.UserManagement.UserLogin.UserLoginDetailsRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final UserLoginDetailsRepository loginDetailRepository;
    private final ServletContext servletContext;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String sessionId = request.getRequestedSessionId();

        if (sessionId != null) {
            UserLoginDetails userLoginDetails = (UserLoginDetails) loginDetailRepository.findBySessionId(sessionId).orElse(null);
            if (userLoginDetails != null) {
                userLoginDetails.setIsActive(false);
                loginDetailRepository.save(userLoginDetails);
            }
        }

        String contextPath = servletContext.getContextPath();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
        // Redirect to login page or show a message
        response.sendRedirect(baseUrl + "/login");
    }
}