package com.example.jkpvt.Connectors.Connector;

import java.util.List;

public interface ConnectorDAO {
    List<Connector> get(ConnectorDTO connectorDTO);
}
