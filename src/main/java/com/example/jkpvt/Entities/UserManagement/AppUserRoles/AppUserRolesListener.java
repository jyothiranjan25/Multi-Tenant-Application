package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Entities.UserManagement.AppUser.AppUser;
import com.example.jkpvt.Entities.UserManagement.AppUser.AppUserService;
import com.example.jkpvt.Entities.UserManagement.Roles.Roles;
import com.example.jkpvt.Entities.UserManagement.Roles.RolesService;
import jakarta.persistence.PrePersist;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
            throw new CommonException(AppUserRolesMessages.APP_USER_IS_MANDATORY);
        }
        if (appUserRoles.getRoles() == null) {
            throw new CommonException(AppUserRolesMessages.ROLE_IS_MANDATORY);
        }
        if (appUserRoles.getUserGroup() == null) {
            throw new CommonException(AppUserRolesMessages.USER_GROUP_IS_MANDATORY);
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
        List<AppUserRolesDTO> appUserRole = applicationContext.getBean(AppUserRolesService.class).get(appUserRolesDTO);
        if (!appUserRole.isEmpty()) {
            throw new CommonException(AppUserRolesMessages.ROLE_ALREADY_EXISTS);
        }
    }
}
