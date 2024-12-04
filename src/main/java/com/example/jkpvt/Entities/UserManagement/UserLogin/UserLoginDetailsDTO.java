package com.example.jkpvt.Entities.UserManagement.UserLogin;

import com.example.jkpvt.Core.General.CommonFilterDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserLoginDetailsDTO extends CommonFilterDTO<UserLoginDetailsDTO> {
    private String username;
    private String sessionId;
    private LocalDateTime loginTime;
    private String ipAddress;
    private Boolean isActive;
}