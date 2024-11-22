package com.example.jkpvt.Connectors.Connector;

public enum ConnectorEnum {
    SSO,EMAIL,DATABASE,STORAGE;

    public static ConnectorEnum[] getConnectorTypes() {
        return ConnectorEnum.values();
    }
}
