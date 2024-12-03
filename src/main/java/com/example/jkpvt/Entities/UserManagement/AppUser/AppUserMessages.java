package com.example.jkpvt.Entities.UserManagement.AppUser;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum AppUserMessages implements MessagesCodes {
    ID_NOT_NULL("APP_USER.ID_NOT_NULL"),
    USER_NAME_NOT_NULL("APP_USER.USER_NAME_NOT_NULL"),
    PASSWORD_NOT_NULL("APP_USER.PASSWORD_NOT_NULL"),
    EMAIL_NOT_NULL("APP_USER.EMAIL_NOT_NULL"),
    USER_NAME_DUPLICATE("APP_USER.USER_NAME_DUPLICATE"),
    EMAIL_DUPLICATE("APP_USER.EMAIL_DUPLICATE"),
    USER_NAME_NOT_FOUND("APP_USER.USER_NAME_NOT_FOUND"),

    ;private final String message;

    AppUserMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
