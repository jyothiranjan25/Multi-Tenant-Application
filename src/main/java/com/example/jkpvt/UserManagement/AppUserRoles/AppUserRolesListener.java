package com.example.jkpvt.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.AppUser.AppUser;
import com.example.jkpvt.UserManagement.AppUser.AppUserService;
import com.example.jkpvt.UserManagement.Modules.ModulesListener;
import com.example.jkpvt.UserManagement.Roles.Roles;
import com.example.jkpvt.UserManagement.Roles.RolesService;
import jakarta.persistence.PrePersist;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.List;

@Component
public class AppUserRolesListener implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppUserRolesListener.applicationContext = applicationContext;
    }

    @PrePersist
    public void prePersist(AppUserRoles appUserRoles) {
        if (appUserRoles.getAppUser() == null) {
            throw new CommonException("AppUser is mandatory");
        }
        if (appUserRoles.getRoles() == null) {
            throw new CommonException("Roles is mandatory");
        }
        if (appUserRoles.getUserGroup() == null || appUserRoles.getUserGroup().isEmpty()) {
            throw new CommonException("UserGroup is mandatory");
        }
        checkConditions(appUserRoles);
        checkDuplicate(appUserRoles);
    }

    private void checkConditions(AppUserRoles appUserRoles) {
        AppUser appUser = applicationContext.getBean(AppUserService.class).getById(appUserRoles.getAppUser().getId());
        Roles roles = applicationContext.getBean(RolesService.class).getById(appUserRoles.getRoles().getId());
    }

    private void checkDuplicate(AppUserRoles appUserRoles) {
        AppUserRolesDTO appUserRolesDTO = new AppUserRolesDTO();
        appUserRolesDTO.setAppUserId(appUserRoles.getAppUser().getId());
        appUserRolesDTO.setRolesId(appUserRoles.getRoles().getId());
        appUserRolesDTO.setUserGroup(appUserRoles.getUserGroup());
        List<AppUserRolesDTO> appUserRole = applicationContext.getBean(AppUserRolesService.class).get(appUserRolesDTO);
        if (!appUserRole.isEmpty()) {
            throw new CommonException("Data already exists");
        }
    }
}
