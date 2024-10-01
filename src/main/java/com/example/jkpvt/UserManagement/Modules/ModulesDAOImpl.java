package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.PaginationUtil.PaginationUtil;
import com.example.jkpvt.UserManagement.Resources.Resources;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ModulesDAOImpl implements ModulesDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private final ModulesMapper mapper;

    public List<ModulesDTO> get(ModulesDTO modulesDTO) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Modules> criteriaQuery = criteriaBuilder.createQuery(Modules.class);
            Root<Modules> root = criteriaQuery.from(Modules.class);

            // Join with Resources table
            Join<Modules, Resources> resourcesJoin = root.join("resources", JoinType.LEFT);

            List<Predicate> predicates = buildPredicates(modulesDTO, criteriaBuilder, root, resourcesJoin);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<Modules> query = entityManager.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, modulesDTO);

            List<Modules> Modules = query.getResultList();
            return mapper.map(Modules);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(ModulesDTO dto, CriteriaBuilder criteriaBuilder, Root<Modules> root, Join<Modules, Resources> resourcesJoin) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getModuleName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("moduleName")), dto.getModuleName().toLowerCase()));
        }
        if (dto.getModuleDescription() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("moduleDescription")), dto.getModuleDescription().toLowerCase()));
        }
        if(dto.getModuleUrl() != null){
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("moduleUrl")), dto.getModuleUrl().toLowerCase()));
        }
//         if (dto.getResourceName() != null) {
//             predicates.add(criteriaBuilder.equal(resourcesJoin.get("resourceName"), dto.getResourceName()));
//         }
        return predicates;
    }
}