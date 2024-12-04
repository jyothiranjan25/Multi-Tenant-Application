package com.example.jkpvt.Core.ExceptionHandling;

import com.example.jkpvt.Core.Messages.Messages;
import com.example.jkpvt.Core.Messages.MessagesCodes;

public class CommonException extends RuntimeException {
    public CommonException(String message) {
        super(message);
    }

    public CommonException(Messages message) {
        super(message.getMessage());
    }

    public CommonException(MessagesCodes codes) {
       Messages message = Messages.getMessage(codes);
        throw new CommonException(message);
    }

    public CommonException(MessagesCodes codes,Object... args) {
        Messages message = Messages.getMessage(codes,args);
        throw new CommonException(message);
    }
}
