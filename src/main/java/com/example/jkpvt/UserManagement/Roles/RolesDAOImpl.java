package com.example.jkpvt.UserManagement.Roles;

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

@Repository
@RequiredArgsConstructor
public class RolesDAOImpl implements RolesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Roles> get(RolesDTO dto) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
            Root<Roles> root = criteriaQuery.from(Roles.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<Roles> query = entityManager.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }


    private List<Predicate> buildPredicates(RolesDTO dto, CriteriaBuilder criteriaBuilder, Root<Roles> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getRoleName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("roleName")), dto.getRoleName().toLowerCase()));
        }
        if (dto.getRoleDescription() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("roleDescription")), dto.getRoleDescription().toLowerCase()));
        }
        return predicates;
    }
}
