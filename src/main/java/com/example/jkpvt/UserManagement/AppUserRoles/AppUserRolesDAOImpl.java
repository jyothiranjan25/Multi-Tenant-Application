package com.example.jkpvt.UserManagement.AppUserRoles;

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
public class AppUserRolesDAOImpl implements AppUserRolesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AppUserRoles> get(AppUserRolesDTO dto) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<AppUserRoles> criteriaQuery = criteriaBuilder.createQuery(AppUserRoles.class);
            Root<AppUserRoles> root = criteriaQuery.from(AppUserRoles.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<AppUserRoles> query = entityManager.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(AppUserRolesDTO dto, CriteriaBuilder criteriaBuilder, Root<AppUserRoles> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getAppUserId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"), dto.getAppUserId()));
        }
        if (dto.getRolesId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("roles").get("id"), dto.getRolesId()));
        }
        if (dto.getUserGroup() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("userGroup")), dto.getUserGroup().toLowerCase()));
        }

        return predicates;
    }
}
