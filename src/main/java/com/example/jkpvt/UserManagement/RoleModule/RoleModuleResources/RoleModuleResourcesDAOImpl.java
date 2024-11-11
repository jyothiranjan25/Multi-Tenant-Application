package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

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
public class RoleModuleResourcesDAOImpl implements RoleModuleResourcesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RoleModuleResources> get(RoleModuleResourcesDTO dto) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<RoleModuleResources> criteriaQuery = criteriaBuilder.createQuery(RoleModuleResources.class);
            Root<RoleModuleResources> root = criteriaQuery.from(RoleModuleResources.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<RoleModuleResources> query = entityManager.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(RoleModuleResourcesDTO dto, CriteriaBuilder criteriaBuilder, Root<RoleModuleResources> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getRoleId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("role").get("id"), dto.getRoleId()));
        }
        if (dto.getModuleId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("module").get("id"), dto.getModuleId()));
        }
        if(dto.getResourceId() != null){
            predicates.add(criteriaBuilder.equal(root.get("resource").get("id"), dto.getResourceId()));
        }
        return predicates;
    }
}
