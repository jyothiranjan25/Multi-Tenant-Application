package com.example.jkpvt.UserManagement.RoleModule;

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
public class RoleModuleDAOImpl implements RoleModuleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RoleModule> get(RoleModuleDTO dto) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<RoleModule> criteriaQuery = criteriaBuilder.createQuery(RoleModule.class);
            Root<RoleModule> root = criteriaQuery.from(RoleModule.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<RoleModule> query = entityManager.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(RoleModuleDTO dto, CriteriaBuilder criteriaBuilder, Root<RoleModule> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        return predicates;
    }
}
