package com.example.jkpvt.Entities.UserManagement.RoleModule;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import jakarta.persistence.PrePersist;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class RoleModuleListener implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        RoleModuleListener.applicationContext = applicationContext;
    }

    @PrePersist
    public void prePersist(RoleModule roleModule) {
        if (roleModule.getRole() == null) {
            throw new CommonException(RoleModuleMessages.ROLE_IS_MANDATORY);
        }
        if (roleModule.getModule() == null) {
            throw new CommonException(RoleModuleMessages.MODULE_IS_MANDATORY);
        }
        if (roleModule.getModuleOrder() == null) {
            roleModule.setModuleOrder(0L);
        }
        checkForDuplicateRoleModule(roleModule);
    }

    private void checkForDuplicateRoleModule(RoleModule roleModule) {
        RoleModule roleModuleList = applicationContext.getBean(RoleModuleService.class).getByRoleAndModuleId(roleModule.getRole().getId(), roleModule.getModule().getId());
        if (roleModuleList != null) {
            throw new CommonException(RoleModuleMessages.ROLE_MODULE_DUPLICATE);
        }
    }
}
