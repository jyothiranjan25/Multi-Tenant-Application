package com.example.jkpvt.Entities.UserManagement.AppUser;

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
public class AppUserDAOImpl implements AppUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<AppUser> get(AppUserDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<AppUser> criteriaQuery = criteriaBuilder.createQuery(AppUser.class);
            Root<AppUser> root = criteriaQuery.from(AppUser.class);

            List<Predicate> predicates = buildPredicates(dto, criteriaBuilder, root);

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            TypedQuery<AppUser> query = session.createQuery(criteriaQuery);

            PaginationUtil.applyPagination(query, dto);

            return query.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private List<Predicate> buildPredicates(AppUserDTO dto, HibernateCriteriaBuilder criteriaBuilder, Root<AppUser> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), dto.getId()));
        }
        if (dto.getUserName() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("userName"), dto.getUserName()));
        }
        if (dto.getEmail() != null) {
            predicates.add(criteriaBuilder.ilike(root.get("email"), dto.getEmail()));
        }
        if (dto.getIsAdmin() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isAdmin"), dto.getIsAdmin()));
        }
        if (dto.getIsActive() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isActive"), dto.getIsActive()));
        }

        return predicates;
    }
}
