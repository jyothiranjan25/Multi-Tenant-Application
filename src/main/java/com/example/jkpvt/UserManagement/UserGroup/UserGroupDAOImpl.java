package com.example.jkpvt.UserManagement.UserGroup;

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
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserGroupDAOImpl implements UserGroupDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserGroupMapper mapper;

    public List<UserGroupDTO> get(UserGroupDTO userGroupDTO) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserGroup> criteriaQuery = criteriaBuilder.createQuery(UserGroup.class);
            Root<UserGroup> root = criteriaQuery.from(UserGroup.class);

            List<Predicate> predicates = buildPredicates(userGroupDTO, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<UserGroup> query = entityManager.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, userGroupDTO);

            List<UserGroup> userGroups = query.getResultList();
            return mapper.map(userGroups);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(UserGroupDTO dto, CriteriaBuilder criteriaBuilder, Root<UserGroup> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getGroupName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("groupName")), dto.getGroupName().toLowerCase()));
        }
        if (dto.getGroupDescription() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("groupDescription")), dto.getGroupDescription().toLowerCase()));
        }
        if (dto.getQualifiedName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("qualifiedName")), dto.getQualifiedName().toLowerCase()));
        }
        if (dto.getParentId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("parentGroup").get("id"), dto.getParentId()));
        }
        return predicates;
    }
}
