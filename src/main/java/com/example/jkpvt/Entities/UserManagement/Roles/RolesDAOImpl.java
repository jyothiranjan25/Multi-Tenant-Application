package com.example.jkpvt.Entities.UserManagement.Roles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.General.CriteriaBuilder.CriteriaBuilderWrapper;
import com.example.jkpvt.Core.Messages.CommonMessages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RolesDAOImpl implements RolesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<Roles> get(RolesDTO dto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<Roles> cbw = new CriteriaBuilderWrapper<>(Roles.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<Roles> cbw, RolesDTO dto) {
        if (dto.getId() != null)
            cbw.Equal("id", dto.getId());

        if (dto.getRoleName() != null)
            cbw.ILike("roleName", dto.getRoleName());

        if (dto.getRoleDescription() != null)
            cbw.ILike("roleDescription", dto.getRoleDescription());
    }
}
