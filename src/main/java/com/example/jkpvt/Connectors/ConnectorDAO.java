package com.example.jkpvt.Connectors;

import java.util.List;

public interface ConnectorDAO {
    List<Connector> get(ConnectorDTO connectorDTO);
}
