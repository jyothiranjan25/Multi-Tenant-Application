package com.example.jkpvt.Connectors.Connector;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ConnectorDAOImpl implements ConnectorDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public List<Connector> get(ConnectorDTO connectorDTO) {
        try(Session session = entityManager.unwrap(Session.class)) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Connector> criteriaQuery = criteriaBuilder.createQuery(Connector.class);
            Root<Connector> root = criteriaQuery.from(Connector.class);

            List<Predicate> predicates = Predicates(criteriaBuilder, root, connectorDTO);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            TypedQuery<Connector> query = session.createQuery(criteriaQuery);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> Predicates(HibernateCriteriaBuilder c, Root<Connector> root, ConnectorDTO connectorDTO) {
        List<Predicate> predicates = new ArrayList<>();

        if (connectorDTO.getId() != null) {
            predicates.add(c.equal(root.get("id"), connectorDTO.getId()));
        }

        if (connectorDTO.getConnectorName() != null) {
            predicates.add(c.ilike(root.get("connectorName"), connectorDTO.getConnectorName()));
        }

        if (connectorDTO.getDescription() != null) {
            predicates.add(c.ilike(root.get("description"), connectorDTO.getDescription()));
        }

        if (connectorDTO.getType() != null) {
            predicates.add(c.ilike(root.get("type"), connectorDTO.getType().toString()));
        }

        return predicates;
    }
}