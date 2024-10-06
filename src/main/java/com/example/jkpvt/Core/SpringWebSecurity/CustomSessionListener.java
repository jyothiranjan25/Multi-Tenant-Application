package com.example.jkpvt.Core.SpringWebSecurity;

import com.example.jkpvt.UserManagement.UserLogin.UserLoginDetails;
import com.example.jkpvt.UserManagement.UserLogin.UserLoginDetailsRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSessionListener extends HttpServlet implements HttpSessionListener {

    private final UserLoginDetailsRepository userLoginDetailsRepository;

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();
        UserLoginDetails userLoginDetailsOpt = (UserLoginDetails) userLoginDetailsRepository.findBySessionId(sessionId).orElse(null);
        if (userLoginDetailsOpt != null) {
            userLoginDetailsOpt.setIsActive(false);
            userLoginDetailsRepository.save(userLoginDetailsOpt);
        }
    }
}

