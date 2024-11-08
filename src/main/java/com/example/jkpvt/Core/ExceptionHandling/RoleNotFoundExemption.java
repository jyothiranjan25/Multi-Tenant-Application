package com.example.jkpvt.Core.ExceptionHandling;

import org.springframework.security.core.AuthenticationException;

public class RoleNotFoundExemption extends AuthenticationException {
    public RoleNotFoundExemption(String msg) {
        super(msg);
    }
}
