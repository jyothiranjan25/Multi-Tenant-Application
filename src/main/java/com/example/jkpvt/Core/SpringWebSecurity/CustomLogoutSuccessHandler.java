package com.example.jkpvt.Core.SpringWebSecurity;

import com.example.jkpvt.UserManagement.UserLogin.UserLoginDetails;
import com.example.jkpvt.UserManagement.UserLogin.UserLoginDetailsRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler extends HttpServlet implements LogoutSuccessHandler {

    private final UserLoginDetailsRepository loginDetailRepository;
    private final ServletContext servletContext;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String sessionId = (String) session.getAttribute("sessionId1");

            UserLoginDetails userLoginDetails = (UserLoginDetails) loginDetailRepository.findBySessionId(sessionId).orElse(null);
            if (userLoginDetails != null) {
                userLoginDetails.setIsActive(false);
                loginDetailRepository.save(userLoginDetails);
            }

            session.invalidate();
        }

        String contextPath = servletContext.getContextPath();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
        // Redirect to login page or show a message
        response.sendRedirect(baseUrl + "/login");
    }
}