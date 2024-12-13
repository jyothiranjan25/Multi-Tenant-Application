package com.example.jkpvt.Entities.UserManagement.Modules;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum ModulesMessages implements MessagesCodes {
    MODULE_NOT_FOUND("MODULES.MODULE_NOT_FOUND"),
    MODULE_NAME_MANDATORY("MODULES.MODULE_NAME_MANDATORY"),
    MODULE_URL_REQUIRED("MODULES.MODULE_URL_REQUIRED"),
    MODULE_PARENT_REQUIRED_CHILD("MODULES.MODULE_PARENT_REQUIRED_CHILD"),
    MODULE_CHILD_REQUIRED_PARENT("MODULES.MODULE_CHILD_REQUIRED_PARENT"),
    MODULE_NAME_DUPLICATE("MODULES.MODULE_NAME_DUPLICATE"),

    ;
    private final String message;

    ModulesMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
