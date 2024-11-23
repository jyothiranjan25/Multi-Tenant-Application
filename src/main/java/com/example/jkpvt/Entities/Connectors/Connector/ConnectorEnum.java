package com.example.jkpvt.Entities.Connectors.Connector;

public enum ConnectorEnum {
    SSO,EMAIL,DATABASE,STORAGE;

    public static ConnectorEnum[] getConnectorTypes() {
        return ConnectorEnum.values();
    }
}
