package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Entities.SearchFilter.SearchFilterService;
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
    private final ConnectorRepository repository;
    private final SearchFilterService searchFilterService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<ConnectorDTO> search(ConnectorDTO connectorDTO) {
        List<Connector> connector = searchFilterService.search(Connector.class, connectorDTO.getSearchTerm(), connectorDTO);
        return mapper.map(connector);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<ConnectorDTO> get(ConnectorDTO connectorDTO) {
        List<Connector> connector = connectorDAO.get(connectorDTO);
        return mapper.map(connector);
    }

    @Transactional
    public ConnectorDTO create(ConnectorDTO connectorDTO) {
        Connector connector = mapper.map(connectorDTO);
        connector = repository.save(connector);
        return mapper.map(connector);
    }

    @Transactional
    public ConnectorDTO update(ConnectorDTO connectorDTO) {
        Connector connector = getById(connectorDTO.getId());
        updateConnectorData(connector, connectorDTO);
        connector = repository.saveAndFlush(connector);
        return mapper.map(connector);
    }

    @Transactional(readOnly = true)
    public Connector getById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            Object[] args = new Object[]{id};
            return new CommonException(ConnectorMessages.ID_NOT_FOUND, args);
        });
    }

    public void updateConnectorData(Connector connector, ConnectorDTO connectorDTO) {
        if (connectorDTO.getConnectorName() != null) {
            connector.setConnectorName(connectorDTO.getConnectorName());
        }
        if (connectorDTO.getDescription() != null) {
            connector.setDescription(connectorDTO.getDescription());
        }
        if (connectorDTO.getType() != null) {
            connector.setType(connectorDTO.getType());
        }
        if (connectorDTO.getStatus() != null) {
            connector.setStatus(connectorDTO.getStatus());
        }
        if (connectorDTO.getIcon() != null) {
            connector.setIcon(connectorDTO.getIcon());
        }
    }
}
