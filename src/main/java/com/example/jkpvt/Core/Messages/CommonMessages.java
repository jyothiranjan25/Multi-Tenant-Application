package com.example.jkpvt.Core.Messages;

public enum CommonMessages implements MessagesCodes {
    APPLICATION_ERROR("Common_Messages.APPLICATION_ERROR"),
    USER_GROUP_NOT_FOUND("Common_Messages.USER_GROUP_NOT_FOUND"),
    DATA_DELETE_SUCCESS("Common_Messages.DATA_DELETE_SUCCESS"),
    DATA_SAVE_SUCCESS("Common_Messages.DATA_SAVE_SUCCESS"),


    ;private final String message;

    CommonMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
