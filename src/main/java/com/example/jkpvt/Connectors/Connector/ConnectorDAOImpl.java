package com.example.jkpvt.Connectors.Connector;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConnectorDAOImpl implements ConnectorDAO{
    public List<Connector> get(ConnectorDTO connectorDTO) {
        return List.of();
    }
}
