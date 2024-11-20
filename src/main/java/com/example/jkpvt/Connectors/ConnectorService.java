package com.example.jkpvt.Connectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectorService {

    private final ConnectorDAO connectorDAO;
    private final ConnectorMapper mapper;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ConnectorDTO> get(ConnectorDTO connectorDTO) {
        List<Connector> connector = connectorDAO.get(connectorDTO);
        return mapper.map(connector);
    }
}
