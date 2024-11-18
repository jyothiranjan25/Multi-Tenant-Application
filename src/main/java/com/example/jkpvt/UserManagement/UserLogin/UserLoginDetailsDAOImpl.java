package com.example.jkpvt.UserManagement.UserLogin;

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
public class UserLoginDetailsDAOImpl implements UserLoginDetailsDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<UserLoginDetails> get(UserLoginDetailsDTO dto) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserLoginDetails> criteriaQuery = criteriaBuilder.createQuery(UserLoginDetails.class);
            Root<UserLoginDetails> root = criteriaQuery.from(UserLoginDetails.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<UserLoginDetails> query = entityManager.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(UserLoginDetailsDTO dto, CriteriaBuilder criteriaBuilder, Root<UserLoginDetails> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getUsername() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), dto.getUsername().toLowerCase()));
        }
        return predicates;
    }
}