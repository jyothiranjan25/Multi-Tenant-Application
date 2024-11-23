package com.example.jkpvt.Entities.UserManagement.UserGroup;

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
public class UserGroupDAOImpl implements UserGroupDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<UserGroup> get(UserGroupDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<UserGroup> criteriaQuery = criteriaBuilder.createQuery(UserGroup.class);
            Root<UserGroup> root = criteriaQuery.from(UserGroup.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<UserGroup> query = session.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(UserGroupDTO dto, HibernateCriteriaBuilder criteriaBuilder, Root<UserGroup> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getGroupName() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("groupName"), dto.getGroupName()));
        }
        if (dto.getGroupDescription() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("groupDescription"), dto.getGroupDescription()));
        }
        if (dto.getQualifiedName() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("qualifiedName"), dto.getQualifiedName()));
        }
        if (dto.getParentId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("parentGroup").get("id"), dto.getParentId()));
        }
        return predicates;
    }
}
