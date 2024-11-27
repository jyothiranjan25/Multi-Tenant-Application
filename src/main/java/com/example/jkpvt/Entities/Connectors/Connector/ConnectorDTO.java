package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Core.General.CommonFilterDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectorDTO extends CommonFilterDTO<ConnectorDTO> {
    private Long id;
    private String connectorName;
    private String description;
    private ConnectorTypeEnum type;
    private Boolean status;
    private String icon;
}
