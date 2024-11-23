package com.example.jkpvt.Entities.Connectors.ConnectorXref;

public enum ConnectorXrefEnum {
    ACTIVE,INACTIVE;

    public static ConnectorXrefEnum[] getConnectorTypes() {
        return ConnectorXrefEnum.values();
    }
}
