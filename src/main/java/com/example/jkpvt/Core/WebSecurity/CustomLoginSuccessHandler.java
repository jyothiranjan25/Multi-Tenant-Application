package com.example.jkpvt.Core.WebSecurity;

import com.example.jkpvt.UserManagement.UserLogin.UserLoginDetails;
import com.example.jkpvt.UserManagement.UserLogin.UserLoginDetailsRepository;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends HttpServlet implements AuthenticationSuccessHandler {

    private final Gson gson;
    private final UserLoginDetailsRepository userLoginDetailsRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            HttpSession session = request.getSession(true);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);

            // Save login details in the database
            saveUserLoginDetails(request, authentication);

            // to be used in the current request
            request.setAttribute("username1", authentication.getName());
            request.setAttribute("sessionId1", session.getId());

            // to be used in system-wide
            request.getSession().setAttribute("username", authentication.getName());
            request.getSession().setAttribute("sessionId", session.getId());

            Map<String, String> userDetails = new HashMap<>();
            userDetails.put("message", "Login successful");
            userDetails.put("username", authentication.getName());
            userDetails.put("sessionId", session.getId());
            userDetails.put("redirectUrl", "home");

            SuccessResponse successResponse = new SuccessResponse(userDetails);

            try (PrintWriter writer = response.getWriter()) {
                writer.write(gson.toJson(successResponse));
                writer.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveUserLoginDetails(HttpServletRequest request, Authentication authentication) {
        try {
            // Store login details in the database
            UserLoginDetails loginDetail = new UserLoginDetails();
            loginDetail.setUsername(authentication.getName());
            loginDetail.setSessionId(request.getSession().getId());
            loginDetail.setLoginTime(LocalDateTime.now());
            loginDetail.setIpAddress(request.getRemoteAddr());
            loginDetail.setIsActive(true);

            userLoginDetailsRepository.save(loginDetail);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class SuccessResponse {
        private final Map<String, String> userDetails;
        public SuccessResponse(Map<String, String> userDetails) {
            this.userDetails = userDetails;
        }
    }
}
