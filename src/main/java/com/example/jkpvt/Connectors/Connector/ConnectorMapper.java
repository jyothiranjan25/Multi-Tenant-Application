package com.example.jkpvt.Connectors.Connector;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConnectorMapper {

    ConnectorDTO map(Connector connector);

    @InheritConfiguration
    List<ConnectorDTO> map(List<Connector> connectors);

    @InheritInverseConfiguration
    Connector map(ConnectorDTO connectorDTO);
}
