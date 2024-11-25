package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Entities.SearchFilter.commonFilterDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectorDTO extends commonFilterDTO {
    private Long id;
    private String connectorName;
    private String description;
    private ConnectorTypeEnum type;
    private Boolean status;
    private String icon;
}
