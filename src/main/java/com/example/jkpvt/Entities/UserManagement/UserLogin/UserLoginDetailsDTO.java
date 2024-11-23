package com.example.jkpvt.Entities.UserManagement.UserLogin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserLoginDetailsDTO {
    private Long id;
    private String username;
    private String sessionId;
    private LocalDateTime loginTime;
    private String ipAddress;
    private Boolean isActive;
}