package com.example.jkpvt.Entities.Connectors.ConnectorConfiguration;

import com.example.jkpvt.Entities.Connectors.ConnectorXref.ConnectorXrefDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectorConfigurationDTO {
    private Long id;
    private ConnectorXrefDTO connectorXref;
    private Long connectorXrefId;
    private String key;
    private String value;
    private boolean secure;
}
