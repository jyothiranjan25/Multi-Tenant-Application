package com.example.jkpvt.Entities.UserManagement.RoleModule;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum RoleModuleMessages implements MessagesCodes {
    ROLE_IS_MANDATORY("ROLES.ROLE_IS_MANDATORY"),
    MODULE_IS_MANDATORY("MODULES.MODULE_IS_MANDATORY"),
    ROLE_MODULE_DUPLICATE("ROLE_MODULE.ROLE_MODULE_DUPLICATE"),

    ;
    private final String message;

    RoleModuleMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
