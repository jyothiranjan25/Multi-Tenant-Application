package com.example.jkpvt.Connectors.ConnectorXref;

import com.example.jkpvt.Connectors.ConnectorConfiguration.ConnectorConfigurationMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ConnectorXrefMapper.class, ConnectorConfigurationMapper.class})
public interface ConnectorXrefMapper {

    @Mapping(target = "connector.id", source = "connectorId")
    ConnectorXref map(ConnectorXrefDTO connectorDTO);

    @InheritConfiguration
    List<ConnectorXrefDTO> map(List<ConnectorXref> connectors);

    @InheritInverseConfiguration
    ConnectorXrefDTO map(ConnectorXref connector);

}
