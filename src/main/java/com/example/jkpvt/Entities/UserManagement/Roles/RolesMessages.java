package com.example.jkpvt.Entities.UserManagement.Roles;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum RolesMessages implements MessagesCodes {
    ID_MANDATORY("ROLES.ID_MANDATORY"),
    ROLE_NAME_MANDATORY("ROLES.ROLE_NAME_MANDATORY"),
    ROLE_NAME_DUPLICATE("ROLES.ROLE_NAME_DUPLICATE"),
    ROLE_NOT_FOUND("ROLES.ROLE_NOT_FOUND"),

    ;private final String message;

    RolesMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
