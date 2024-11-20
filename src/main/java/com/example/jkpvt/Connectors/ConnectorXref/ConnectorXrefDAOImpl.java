package com.example.jkpvt.Connectors.ConnectorXref;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConnectorXrefDAOImpl implements ConnectorXrefDAO {
    public List<ConnectorXref> get(ConnectorXrefDTO connectorXrefDTO) {
        return List.of();
    }
}
