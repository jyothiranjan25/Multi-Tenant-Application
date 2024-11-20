package com.example.jkpvt.Connectors.ConnectorXref;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ConnectorXrefMapper.class)
public interface ConnectorXrefMapper {

    @Mapping(target = "connector", source = "connector")
    ConnectorXrefDTO map(ConnectorXref connector);

    @InheritConfiguration
    List<ConnectorXrefDTO> map(List<ConnectorXref> connectors);

    @InheritInverseConfiguration
    @Mapping(target = "connector.id", source = "connectorId")
    ConnectorXref map(ConnectorXrefDTO connectorDTO);
}
