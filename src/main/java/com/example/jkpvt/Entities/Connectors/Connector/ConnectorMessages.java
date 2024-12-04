package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Core.Messages.MessagesCodes;

public enum ConnectorMessages implements MessagesCodes {
    ID_NOT_FOUND("CONNECTOR.ID_NOT_FOUND"),
    DUPLICATE_CONNECTOR("CONNECTOR.DUPLICATE_CONNECTOR"),
    CONNECTOR_OBJECT_NOT_FOUND("CONNECTOR.OBJECT_NOT_FOUND");

    private final String message;

    ConnectorMessages(String message) {
        this.message = message;
    }

    @Override
    public String getValue() {
        return message;
    }
}
