package com.example.jkpvt.Entities.UserManagement.Roles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolesListener implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        RolesListener.applicationContext = applicationContext;
    }

    @PrePersist
    public void prePersist(Roles roles) {
        if (roles.getRoleName() == null || roles.getRoleName().isEmpty()) {
            throw new CommonException(RolesMessages.ROLE_NAME_MANDATORY);
        }
        checkForDuplicateRoleName(roles);
    }

    @PreUpdate
    public void preUpdate(Roles roles) {
        if (roles.getId() == null) {
            throw new CommonException(RolesMessages.ID_MANDATORY);
        }
        checkForDuplicateRoleName(roles);
    }

    @PreRemove
    public void preRemove(Roles roles) {
        if (roles.getId() == null) {
            throw new CommonException(RolesMessages.ID_MANDATORY);
        }
    }

    private void checkForDuplicateRoleName(Roles roles) {
        RolesDTO filter = new RolesDTO();
        filter.setRoleName(roles.getRoleName().toLowerCase());
        List<RolesDTO> rolesList = applicationContext.getBean(RolesService.class).get(filter);
        rolesList.removeIf(x -> x.getId().equals(roles.getId()));
        if (!rolesList.isEmpty()) {
            throw new CommonException(RolesMessages.ROLE_NAME_DUPLICATE, roles.getRoleName());
        }
    }
}
