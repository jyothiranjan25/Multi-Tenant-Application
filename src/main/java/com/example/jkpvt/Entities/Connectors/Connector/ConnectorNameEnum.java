package com.example.jkpvt.Entities.Connectors.Connector;

public enum ConnectorNameEnum {
    AMAZON, GOOGLE, MICROSOFT;

    public static ConnectorNameEnum[] get() {
        return ConnectorNameEnum.values();
    }
}
