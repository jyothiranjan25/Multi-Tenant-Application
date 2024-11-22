package com.example.jkpvt.Connectors.ConnectorXref;

import com.example.jkpvt.Connectors.Connector.ConnectorDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectorXrefDTO {
    private Long id;
    private String Name;
    private String description;
    private ConnectorXrefEnum status;
    private Long connectorId;
    private ConnectorDTO connector;
}
