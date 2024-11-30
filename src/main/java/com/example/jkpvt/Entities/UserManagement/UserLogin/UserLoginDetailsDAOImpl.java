package com.example.jkpvt.Entities.UserManagement.UserLogin;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.General.CriteriaBuilder.CriteriaBuilderWrapper;
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
public class UserLoginDetailsDAOImpl implements UserLoginDetailsDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<UserLoginDetails> get(UserLoginDetailsDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<UserLoginDetails> cbw = new CriteriaBuilderWrapper<>(UserLoginDetails.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<UserLoginDetails> cbw, UserLoginDetailsDTO dto) {
        if(dto.getId() != null)
            cbw.Equal("id", dto.getId());

        if(dto.getUsername() != null)
            cbw.ILike("username", dto.getUsername());
    }
}