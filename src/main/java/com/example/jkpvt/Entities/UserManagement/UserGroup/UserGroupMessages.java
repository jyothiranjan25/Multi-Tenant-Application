package com.example.jkpvt.Entities.UserManagement.UserGroup;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum UserGroupMessages implements MessagesCodes {
    ID_MANDATORY("USER_GROUP.ID_MANDATORY"),
    USER_GROUP_NAME_MANDATORY("USER_GROUP.USER_GROUP_NAME_MANDATORY"),
    USER_GROUP_NAME_DUPLICATE("USER_GROUP.USER_GROUP_NAME_DUPLICATE"),
    USER_GROUP_HAS_CHILDREN("USER_GROUP.USER_GROUP_HAS_CHILDREN"),
    USER_GROUP_NOT_FOUND("USER_GROUP.USER_GROUP_NOT_FOUND"),

    ;private final String message;

    UserGroupMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
