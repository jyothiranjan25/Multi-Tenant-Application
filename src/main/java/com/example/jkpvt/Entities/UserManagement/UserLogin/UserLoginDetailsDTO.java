package com.example.jkpvt.Entities.UserManagement.UserLogin;

import com.example.jkpvt.Entities.SearchFilter.commonFilterDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserLoginDetailsDTO extends commonFilterDTO {
    private Long id;
    private String username;
    private String sessionId;
    private LocalDateTime loginTime;
    private String ipAddress;
    private Boolean isActive;
}