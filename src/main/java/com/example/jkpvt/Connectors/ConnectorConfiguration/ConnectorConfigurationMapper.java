package com.example.jkpvt.Connectors.ConnectorConfiguration;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConnectorConfigurationMapper {

    @Mapping(source = "connectorXref", target = "connectorXref")
    ConnectorConfigurationDTO map(ConnectorConfiguration connectorConfiguration);

    @InheritConfiguration
    List<ConnectorConfigurationDTO> map(List<ConnectorConfiguration> connectorConfigurations);

    @InheritInverseConfiguration
    @Mapping(source = "connectorXrefId", target = "connectorXref.id")
    ConnectorConfiguration map(ConnectorConfigurationDTO connectorConfigurationDTO);

}
