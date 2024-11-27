package com.example.jkpvt.Core.ExceptionHandling;

import com.example.jkpvt.Core.Messages.Messages;

public class CommonException extends RuntimeException {
    public CommonException(String message) {
        super(message);
    }

    public CommonException(Messages message) {
        super(message.getMessage());
    }
}
