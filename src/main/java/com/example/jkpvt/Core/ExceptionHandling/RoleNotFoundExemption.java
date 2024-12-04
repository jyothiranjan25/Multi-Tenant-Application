package com.example.jkpvt.Core.ExceptionHandling;

import com.example.jkpvt.Core.Messages.Messages;
import org.springframework.security.core.AuthenticationException;

public class RoleNotFoundExemption extends AuthenticationException {
    public RoleNotFoundExemption(String msg) {
        super(msg);
    }

    public RoleNotFoundExemption(Messages message) {
        super(message.getMessage());
    }
}
