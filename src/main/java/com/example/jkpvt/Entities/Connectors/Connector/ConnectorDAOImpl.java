package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.General.CriteriaBuilder.CriteriaBuilderWrapper;
import com.example.jkpvt.Core.Messages.CommonMessages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ConnectorDAOImpl implements ConnectorDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public List<Connector> get(ConnectorDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<Connector> cbw = new CriteriaBuilderWrapper<>(Connector.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<Connector> cbw, ConnectorDTO dto) {
        if(dto.getId() != null)
            cbw.Equal("id", dto.getId());
        if(dto.getConnectorName() != null)
            cbw.ILike("connectorName", dto.getConnectorName());
        if(dto.getDescription() != null)
            cbw.ILike("description", dto.getDescription());
        if(dto.getType() != null)
            cbw.ILike("type", dto.getType().toString());
    }
}