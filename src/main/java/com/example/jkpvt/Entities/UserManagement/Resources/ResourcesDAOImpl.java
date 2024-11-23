package com.example.jkpvt.Entities.UserManagement.Resources;

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
public class ResourcesDAOImpl implements ResourcesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<Resources> get(ResourcesDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Resources> criteriaQuery = criteriaBuilder.createQuery(Resources.class);
            Root<Resources> root = criteriaQuery.from(Resources.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<Resources> query = session.createQuery(criteriaQuery);

            // Apply pagination
            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(ResourcesDTO dto, HibernateCriteriaBuilder criteriaBuilder, Root<Resources> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getResourceName() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("resourceName"), dto.getResourceName()));
        }
        if (dto.getResourceFullName() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("resourceFullName"), dto.getResourceFullName()));
        }
        if (dto.getResourceDescription() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("resourceDescription"), dto.getResourceDescription()));
        }
        if (dto.getResourceUrl() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("resourceUrl"), dto.getResourceUrl()));
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
