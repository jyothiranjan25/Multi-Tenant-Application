package com.example.jkpvt.Entities.UserManagement.Modules;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.PaginationUtil.PaginationUtil;
import com.example.jkpvt.Entities.UserManagement.Resources.Resources;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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
public class ModulesDAOImpl implements ModulesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<Modules> get(ModulesDTO modulesDTO) {
        try(Session session = entityManager.unwrap(Session.class)) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Modules> criteriaQuery = criteriaBuilder.createQuery(Modules.class);
            Root<Modules> root = criteriaQuery.from(Modules.class);

            // Join with Resources table
            Join<Modules, Resources> resourcesJoin = root.join("resources", JoinType.LEFT);

            List<Predicate> predicates = buildPredicates(modulesDTO, criteriaBuilder, root, resourcesJoin);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<Modules> query = session.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, modulesDTO);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(ModulesDTO dto, HibernateCriteriaBuilder criteriaBuilder, Root<Modules> root, Join<Modules, Resources> resourcesJoin) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getModuleName() != null) {
           predicates.add(criteriaBuilder.ilike(root.get("moduleName"), dto.getModuleName()));
        }
        if (dto.getModuleDescription() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("moduleDescription"), dto.getModuleDescription()));
        }
        if (dto.getModuleUrl() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("moduleUrl"), dto.getModuleUrl()));
        }
//         if (dto.getResourceName() != null) {
//             predicates.add(criteriaBuilder.equal(resourcesJoin.get("resourceName"), dto.getResourceName()));
//         }
        return predicates;
    }
}