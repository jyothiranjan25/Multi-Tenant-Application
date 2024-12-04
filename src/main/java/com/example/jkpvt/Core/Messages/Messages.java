package com.example.jkpvt.Core.Messages;

import lombok.Getter;
import lombok.Setter;

import java.util.IllegalFormatException;

@Getter
@Setter
public class  Messages {
    private String code;
    private Object[] args;
    private boolean returnCodeAsValue = false;

    public Messages(String code, Object[] args) {
        this(code);
        this.args = args;
    }

    public Messages(String code) {
        this();
        setValueFromCode(code);
    }

    public Messages() {}

    private void setValueFromCode(String code) {
        this.code = ApplicationText.get(code);
    }

    public static Messages getMessage(MessagesCodes code, Object[] args) {
        return new Messages(code.getValue(), args);
    }

    public static Messages getMessage(MessagesCodes code) {
        return new Messages(code.getValue());
    }

    public static Messages getMessage(String value, boolean returnCodeAsValue) {
        Messages message = new Messages();
        message.setCode(value);
        message.setReturnCodeAsValue(returnCodeAsValue);
        return message;
    }

    public String getMessage() {
        if (returnCodeAsValue) {
            return code;
        }
        return getFormattedString(this.code, this.args);
    }

    public static String getFormattedString(String message, Object[] args) {
        if (args == null) {
            return message;
        }
        try {
            return String.format(message, args);
        } catch (IllegalFormatException e) {
            return message;
        }
    }
}