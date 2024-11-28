package com.example.jkpvt.Core.Messages;

public enum CommonMessages implements MessagesCodes {
    APPLICATION_ERROR("Common_Messages.APPLICATION_ERROR");

    private final String message;

    CommonMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
