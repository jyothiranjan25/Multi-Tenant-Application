package com.example.jkpvt.Connectors.ConnectorXref;

public enum ConnectorXrefEnum {
    ACTIVE,INACTIVE;

    public static ConnectorXrefEnum[] getConnectorTypes() {
        return ConnectorXrefEnum.values();
    }
}
