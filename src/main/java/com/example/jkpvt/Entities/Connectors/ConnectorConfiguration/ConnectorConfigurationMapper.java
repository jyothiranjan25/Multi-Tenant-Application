package com.example.jkpvt.Entities.Connectors.ConnectorConfiguration;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConnectorConfigurationMapper {

    @Mapping(source = "connectorXrefId", target = "connectorXref.id")
    ConnectorConfiguration map(ConnectorConfigurationDTO connectorConfigurationDTO);

    @InheritConfiguration
    List<ConnectorConfigurationDTO> map(List<ConnectorConfiguration> connectorConfigurations);

    @InheritInverseConfiguration
    @Mapping(target = "connectorXref", ignore = true)
    ConnectorConfigurationDTO map(ConnectorConfiguration connectorConfiguration);

}
