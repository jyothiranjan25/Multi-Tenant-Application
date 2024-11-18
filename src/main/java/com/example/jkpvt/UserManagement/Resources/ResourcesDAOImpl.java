package com.example.jkpvt.UserManagement.Resources;

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
public class ResourcesDAOImpl implements ResourcesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Resources> get(ResourcesDTO dto) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Resources> criteriaQuery = criteriaBuilder.createQuery(Resources.class);
            Root<Resources> root = criteriaQuery.from(Resources.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<Resources> query = entityManager.createQuery(criteriaQuery);

            // Apply pagination
            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(ResourcesDTO dto, CriteriaBuilder criteriaBuilder, Root<Resources> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getResourceName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("resourceName")), dto.getResourceName().toLowerCase()));
        }
        if (dto.getResourceFullName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("resourceFullName")), dto.getResourceFullName().toLowerCase()));
        }
        if (dto.getResourceDescription() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("resourceDescription")), dto.getResourceDescription().toLowerCase()));
        }
        if (dto.getResourceUrl() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("resourceUrl")), dto.getResourceUrl().toLowerCase()));
        }
        if (dto.getShowInMenu() != null) {
            boolean showInMenu = dto.getShowInMenu();
            predicates.add(criteriaBuilder.equal(root.get("showInMenu"), showInMenu));
        }
        if (dto.getResourceOrder() != null) {
            predicates.add(criteriaBuilder.equal(root.get("resourceOrder"), dto.getResourceOrder()));
        }
        if (dto.getParentId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("parentResource").get("id"), dto.getParentId()));
        }
        return predicates;
    }
}
