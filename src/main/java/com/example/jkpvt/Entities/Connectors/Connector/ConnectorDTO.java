package com.example.jkpvt.Entities.Connectors.Connector;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectorDTO {
    private Long id;
    private String connectorName;
    private String description;
    private ConnectorEnum type;
    private Boolean status;
    private String icon;
}
