package com.example.jkpvt.Entities.Connectors.ConnectorXref;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.General.CriteriaBuilder.CriteriaBuilderWrapper;
import com.example.jkpvt.Core.Messages.CommonMessages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConnectorXrefDAOImpl implements ConnectorXrefDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ConnectorXref> get(ConnectorXrefDTO dto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<ConnectorXref> cbw = new CriteriaBuilderWrapper<>(ConnectorXref.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<ConnectorXref> cbw, ConnectorXrefDTO dto) {
        if (dto.getId() != null)
            cbw.Equal("id", dto.getId());
        if (dto.getName() != null)
            cbw.ILike("name", dto.getName());
        if (dto.getDescription() != null)
            cbw.ILike("description", dto.getDescription());
        if (dto.getStatus() != null)
            cbw.Equal("status", dto.getStatus());
    }
}
