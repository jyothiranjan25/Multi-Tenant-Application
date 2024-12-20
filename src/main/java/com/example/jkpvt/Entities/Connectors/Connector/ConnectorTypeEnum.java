package com.example.jkpvt.Entities.Connectors.Connector;

public enum ConnectorTypeEnum {
    SSO, EMAIL, DATABASE, STORAGE;

    public static ConnectorTypeEnum[] get() {
        return ConnectorTypeEnum.values();
    }
}
