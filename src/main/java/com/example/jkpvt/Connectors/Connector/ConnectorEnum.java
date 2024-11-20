package com.example.jkpvt.Connectors.Connector;

public enum ConnectorEnum {
    SSO,EMAIL,DATABASE,CLOUD_SERVICES;

    public static ConnectorEnum[] getConnectorTypes() {
        return ConnectorEnum.values();
    }
}
