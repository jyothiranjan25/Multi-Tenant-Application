package com.example.jkpvt.Connectors.ConnectorXref;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.PaginationUtil.PaginationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.jkpvt.Core.PaginationUtil.PaginationUtil.addUserGroupFilter;

@Repository
@RequiredArgsConstructor
public class ConnectorXrefDAOImpl implements ConnectorXrefDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ConnectorXref> get(ConnectorXrefDTO dto) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ConnectorXref> criteriaQuery = criteriaBuilder.createQuery(ConnectorXref.class);
            Root<ConnectorXref> root = criteriaQuery.from(ConnectorXref.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<ConnectorXref> query = entityManager.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(ConnectorXrefDTO dto, CriteriaBuilder criteriaBuilder, Root<ConnectorXref> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), dto.getName().toLowerCase()));
        }
        addUserGroupFilter(predicates, root, criteriaBuilder);
        return predicates;
    }
}
