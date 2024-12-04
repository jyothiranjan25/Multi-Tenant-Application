package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum AppUserRolesMessages implements MessagesCodes {
    APP_USER_ROLE_NOT_FOUND("APP_USER_ROLE.APP_USER_ROLE_NOT_FOUND"),
    APP_USER_IS_MANDATORY("APP_USER_ROLE.APP_USER_IS_MANDATORY"),
    ROLE_IS_MANDATORY("ROLES.ROLE_IS_MANDATORY"),
    USER_GROUP_IS_MANDATORY("APP_USER_ROLE.USER_GROUP_IS_MANDATORY"),
    ROLE_ALREADY_EXISTS("APP_USER_ROLE.ROLE_ALREADY_EXISTS"),

    ;private final String message;

    AppUserRolesMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
