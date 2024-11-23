package com.example.jkpvt.Entities.Connectors.ConnectorXref;

import com.example.jkpvt.Entities.Connectors.Connector.ConnectorDTO;
import com.example.jkpvt.Entities.Connectors.ConnectorConfiguration.ConnectorConfigurationDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConnectorXrefDTO {
    private Long id;
    private String Name;
    private String description;
    private ConnectorXrefEnum status;
    private Long connectorId;
    private ConnectorDTO connector;
    private List<ConnectorConfigurationDTO> connectorConfigurations;
}
