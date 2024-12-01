package com.example.jkpvt.Entities.UserManagement.Modules;

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
public class ModulesDAOImpl implements ModulesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<Modules> get(ModulesDTO dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilderWrapper<Modules> cbw = new CriteriaBuilderWrapper<>(Modules.class, session, dto);
            addPredicate(cbw, dto);
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private void addPredicate(CriteriaBuilderWrapper<Modules> cbw, ModulesDTO dto) {
        if(dto.getId() != null)
            cbw.Equal("id", dto.getId());

        if(dto.getModuleName() != null)
            cbw.ILike("moduleName", dto.getModuleName());

        if(dto.getModuleDescription() != null)
            cbw.ILike("moduleDescription", dto.getModuleDescription());

        if(dto.getModuleUrl() != null)
            cbw.ILike("moduleUrl", dto.getModuleUrl());
    }
}