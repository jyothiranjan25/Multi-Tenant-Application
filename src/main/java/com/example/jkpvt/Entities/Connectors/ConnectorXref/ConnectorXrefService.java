package com.example.jkpvt.Entities.Connectors.ConnectorXref;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectorXrefService {

    private final ConnectorXrefDAO connectorDAO;
    private final ConnectorXrefMapper mapper;
    private final ConnectorXrefRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ConnectorXrefDTO> get(ConnectorXrefDTO connectorDTO) {
        List<ConnectorXref> connector = connectorDAO.get(connectorDTO);
        return mapper.map(connector);
    }

    @Transactional
    public ConnectorXrefDTO create(ConnectorXrefDTO connectorDTO) {
        try {
            ConnectorXref connector = mapper.map(connectorDTO);
            connector = repository.save(connector);
            return mapper.map(connector);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public ConnectorXrefDTO update(ConnectorXrefDTO connectorDTO) {
        try {
            ConnectorXref connector = getById(connectorDTO.getId());
            if (connectorDTO.getName() != null) {
                connector.setName(connectorDTO.getName());
            }
            if (connectorDTO.getDescription() != null) {
                connector.setDescription(connectorDTO.getDescription());
            }
            if (connectorDTO.getStatus() != null) {
                connector.setStatus(connectorDTO.getStatus());
            }
            connector = repository.save(connector);
            return mapper.map(connector);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String delete(ConnectorXrefDTO connectorDTO) {
        try {
            if (repository.existsById(connectorDTO.getId())) {
                repository.deleteById(connectorDTO.getId());
                return "Data deleted successfully";
            } else {
                throw new CommonException("Data not found");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public ConnectorXref getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommonException("Connector not found"));
    }
}
