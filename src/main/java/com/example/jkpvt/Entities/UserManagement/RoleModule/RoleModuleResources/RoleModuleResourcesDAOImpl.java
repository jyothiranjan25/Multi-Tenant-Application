package com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources;

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
public class RoleModuleResourcesDAOImpl implements RoleModuleResourcesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<RoleModuleResources> get(RoleModuleResourcesDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<RoleModuleResources> cbw = new CriteriaBuilderWrapper<>(RoleModuleResources.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<RoleModuleResources> cbw, RoleModuleResourcesDTO dto) {
        if(dto.getId() != null)
            cbw.Equal("id", dto.getId());


        if (dto.getRoleId() != null) {
            cbw.join("role");
            cbw.Equal("role.id", dto.getRoleId());
        }

        if (dto.getModuleId() != null) {
            cbw.join("module");
            cbw.Equal("module.id", dto.getModuleId());
        }

        if(dto.getResourceId() != null){
            cbw.join("resource");
            cbw.Equal("resource.id", dto.getResourceId());
        }
    }
}
