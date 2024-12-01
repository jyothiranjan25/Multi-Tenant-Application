package com.example.jkpvt.Entities.Connectors.ConnectorXref;

import com.example.jkpvt.Core.General.CommonFilterDTO;
import com.example.jkpvt.Entities.Connectors.Connector.ConnectorDTO;
import com.example.jkpvt.Entities.Connectors.ConnectorConfiguration.ConnectorConfigurationDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConnectorXrefDTO extends CommonFilterDTO<ConnectorXrefDTO> {
    private String Name;
    private String description;
    private ConnectorXrefEnum status;
    private Long connectorId;
    private ConnectorDTO connector;
    private List<ConnectorConfigurationDTO> connectorConfigurations;
}
