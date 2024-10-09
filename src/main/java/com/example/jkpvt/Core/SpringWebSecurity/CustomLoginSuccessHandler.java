package com.example.jkpvt.Core.SpringWebSecurity;

import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import com.example.jkpvt.UserManagement.UserLogin.UserLoginDetails;
import com.example.jkpvt.UserManagement.UserLogin.UserLoginDetailsRepository;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final Gson gson;
    private final UserLoginDetailsRepository userLoginDetailsRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);

            // Save login details in the database
            saveUserLoginDetails(request, authentication);

            // To be used in system-wide session management
            HttpSession session = request.getSession();
            session.setAttribute("username", authentication.getName());
            session.setAttribute("sessionId", request.getSession().getId());

            AppUserDTO appUserDTO = (AppUserDTO) session.getAttribute("appUser");

            String redirectUrl;
            if(appUserDTO.getAppUserRoles().size() > 1){
                redirectUrl = "selectRole";
            }else {
                redirectUrl = "home";
            }

            SuccessResponse successResponse = new SuccessResponse();
            successResponse.setRedirectUrl(redirectUrl);
            successResponse.setMessage("Login successful");
            successResponse.setSessionId(request.getSession().getId());
            successResponse.setUserDetails(appUserDTO);

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

    @Getter
    @Setter
    private static class SuccessResponse {
        private String redirectUrl;
        private String message;
        private String sessionId;
        private AppUserDTO userDetails;
    }
}

