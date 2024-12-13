package com.example.jkpvt.Entities.UserManagement.Resources;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum ResourcesMessages implements MessagesCodes {
    ID_MANDATORY("RESOURCES.ID_MANDATORY"),
    RESOURCE_NOT_FOUND("RESOURCES.RESOURCE_NOT_FOUND"),
    RESOURCE_NAME_MANDATORY("RESOURCES.RESOURCE_NAME_MANDATORY"),
    RESOURCE_PARENT_ID_REQUIRED("RESOURCES.RESOURCE_PARENT_ID_REQUIRED"),
    RESOURCES_URL_REQUIRED("RESOURCES.RESOURCES_URL_REQUIRED"),
    RESOURCES_URL_INVALID("RESOURCES.RESOURCES_URL_INVALID"),
    RESOURCES_NAME_EXISTS("RESOURCES.RESOURCES_NAME_EXISTS"),
    RESOURCES_HAS_CHILDREN("RESOURCES.RESOURCES_HAS_CHILDREN"),
    RESOURCES_HAS_PARENT("RESOURCES.RESOURCES_HAS_PARENT"),

    ;
    private final String message;

    ResourcesMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
