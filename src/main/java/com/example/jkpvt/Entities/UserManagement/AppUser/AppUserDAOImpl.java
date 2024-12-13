package com.example.jkpvt.Entities.UserManagement.AppUser;

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
public class AppUserDAOImpl implements AppUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<AppUser> get(AppUserDTO dto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<AppUser> cbw = new CriteriaBuilderWrapper<>(AppUser.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<AppUser> cbw, AppUserDTO dto) {
        if (dto.getId() != null)
            cbw.Equal("id", dto.getId());
        if (dto.getUserName() != null)
            cbw.ILike("userName", dto.getUserName());
        if (dto.getEmail() != null)
            cbw.ILike("email", dto.getEmail());
        if (dto.getIsAdmin() != null)
            cbw.Equal("isAdmin", dto.getIsAdmin());
        if (dto.getIsActive() != null)
            cbw.Equal("isActive", dto.getIsActive());
    }
}
