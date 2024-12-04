package com.example.jkpvt.Entities.UserManagement.AppUser;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum AppUserMessages implements MessagesCodes {
    ID_MANDATORY("APP_USER.ID_MANDATORY"),
    USER_NAME_MANDATORY("APP_USER.USER_NAME_MANDATORY"),
    PASSWORD_MANDATORY("APP_USER.PASSWORD_MANDATORY"),
    EMAIL_MANDATORY("APP_USER.EMAIL_MANDATORY"),
    USER_NAME_DUPLICATE("APP_USER.USER_NAME_DUPLICATE"),
    EMAIL_DUPLICATE("APP_USER.EMAIL_DUPLICATE"),
    USER_NOT_FOUND("APP_USER.USER_NOT_FOUND"),

    ;private final String message;

    AppUserMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
