package com.example.jkpvt.UserManagement.UserLogin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_login_details")
public class UserLoginDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "session_id", nullable = false, unique = true)
    private String sessionId;

    @Column(name = "login_time", nullable = false)
    private LocalDateTime loginTime;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
