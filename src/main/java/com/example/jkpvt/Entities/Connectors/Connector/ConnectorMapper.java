package com.example.jkpvt.Entities.Connectors.Connector;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConnectorMapper {

    Connector map(ConnectorDTO connectorDTO);

    @InheritConfiguration
    List<ConnectorDTO> map(List<Connector> connectors);

    @InheritInverseConfiguration
    ConnectorDTO map(Connector connector);
}
