package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.General.CriteriaBuilder.CriteriaBuilderWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AppUserRolesDAOImpl implements AppUserRolesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<AppUserRoles> get(AppUserRolesDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<AppUserRoles> cbw = new CriteriaBuilderWrapper<>(AppUserRoles.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<AppUserRoles> cbw, AppUserRolesDTO dto) {
        if(dto.getId() != null)
            cbw.Equal("id", dto.getId());

        cbw.join("appUser");
        if(dto.getAppUserId() != null)
            cbw.Equal("appUser.id", dto.getAppUserId());

        cbw.join("roles", "r", JoinType.INNER);
        if(dto.getRolesId() != null)
            cbw.Equal("r.id", dto.getRolesId());
    }
}
