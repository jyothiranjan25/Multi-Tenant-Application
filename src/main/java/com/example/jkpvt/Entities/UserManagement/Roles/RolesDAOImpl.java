package com.example.jkpvt.Entities.UserManagement.Roles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.PaginationUtil.PaginationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RolesDAOImpl implements RolesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<Roles> get(RolesDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
            Root<Roles> root = criteriaQuery.from(Roles.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<Roles> query = session.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }


    private List<Predicate> buildPredicates(RolesDTO dto, HibernateCriteriaBuilder criteriaBuilder, Root<Roles> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getRoleName() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("roleName"), dto.getRoleName()));
        }
        if (dto.getRoleDescription() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("roleDescription"), dto.getRoleDescription()));
        }
        return predicates;
    }
}
