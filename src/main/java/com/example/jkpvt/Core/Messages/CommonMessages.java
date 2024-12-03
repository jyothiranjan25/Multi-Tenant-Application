package com.example.jkpvt.Core.Messages;

public enum CommonMessages implements MessagesCodes {
    APPLICATION_ERROR("Common_Messages.APPLICATION_ERROR"),
    USER_GROUP_NOT_FOUND("Common_Messages.USER_GROUP_NOT_FOUND"),
    DATA_DELETED_SUCCESSFULLY("Common_Messages.DATA_DELETED_SUCCESSFULLY"),


    ;private final String message;

    CommonMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
