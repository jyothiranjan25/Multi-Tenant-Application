package com.example.jkpvt.UserManagement.RoleModule;

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
            throw new RuntimeException("Role is mandatory");
        }
        if (roleModule.getModule() == null) {
            throw new RuntimeException("Module is mandatory");
        }
        if(roleModule.getModelOrder() == null) {
            roleModule.setModelOrder(0L);
        }
    }
}
