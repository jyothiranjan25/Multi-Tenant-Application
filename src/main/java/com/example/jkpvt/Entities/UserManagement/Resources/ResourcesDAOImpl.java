package com.example.jkpvt.Entities.UserManagement.Resources;

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
public class ResourcesDAOImpl implements ResourcesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<Resources> get(ResourcesDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<Resources> cbw = new CriteriaBuilderWrapper<>(Resources.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<Resources> cbw, ResourcesDTO dto) {
        if(dto.getId() != null)
            cbw.Equal("id", dto.getId());
        if (dto.getResourceName() != null) {
            cbw.ILike("resourceName", dto.getResourceName());
        }
        if (dto.getResourceFullName() != null) {
            cbw.ILike("resourceFullName", dto.getResourceFullName());
        }
        if (dto.getResourceDescription() != null) {
            cbw.ILike("resourceDescription", dto.getResourceDescription());
        }
        if (dto.getResourceUrl() != null) {
            cbw.ILike("resourceUrl", dto.getResourceUrl());
        }
        if (dto.getShowInMenu() != null) {
            cbw.Equal("showInMenu", dto.getShowInMenu());
        }
        if (dto.getResourceOrder() != null) {
            cbw.Equal("resourceOrder", dto.getResourceOrder());
        }
        if (dto.getParentId() != null) {
            cbw.join("parentResource");
            cbw.Equal("parentResource.id", dto.getParentId());
        }
    }
}
