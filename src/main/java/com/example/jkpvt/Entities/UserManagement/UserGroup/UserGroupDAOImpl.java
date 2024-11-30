package com.example.jkpvt.Entities.UserManagement.UserGroup;

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
public class UserGroupDAOImpl implements UserGroupDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<UserGroup> get(UserGroupDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<UserGroup> cbw = new CriteriaBuilderWrapper<>(UserGroup.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<UserGroup> cbw, UserGroupDTO dto) {
        if(dto.getId() != null)
            cbw.Equal("id", dto.getId());

        if(dto.getGroupName() != null)
            cbw.ILike("groupName", dto.getGroupName());

        if(dto.getGroupDescription() != null)
            cbw.ILike("groupDescription", dto.getGroupDescription());

        if(dto.getQualifiedName() != null)
            cbw.ILike("qualifiedName", dto.getQualifiedName());

        if(dto.getParentId() != null){
            cbw.join("parentGroup");
            cbw.Equal("parentGroup.id", dto.getParentId());
        }
    }
}
