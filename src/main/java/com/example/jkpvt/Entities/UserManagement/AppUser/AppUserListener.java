package com.example.jkpvt.Entities.UserManagement.AppUser;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppUserListener implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppUserListener.applicationContext = applicationContext;
    }

    @PrePersist
    public void prePersist(AppUser appUser) {
        conditionCheck(appUser);
        checkForDuplicateUserName(appUser);
        checkForDuplicateEmail(appUser);
    }

    @PreUpdate
    public void preUpdate(AppUser appUser) {
        conditionCheck(appUser);
        checkForDuplicateUserName(appUser);
        checkForDuplicateEmail(appUser);
    }

    @PreRemove
    public void preRemove(AppUser appUser) {
        if (appUser.getId() == null) {
            throw new CommonException(AppUserMessages.ID_MANDATORY);
        }
    }

    public void conditionCheck(AppUser appUser) {
        if (appUser.getUserName() == null) {
            throw new CommonException(AppUserMessages.USER_NAME_MANDATORY);
        }
        if (appUser.getPassword() == null || appUser.getPassword().isEmpty()) {
            throw new CommonException(AppUserMessages.PASSWORD_MANDATORY);
        }
        if (appUser.getEmail() == null) {
            throw new CommonException(AppUserMessages.EMAIL_MANDATORY);
        }
        if (appUser.getIsActive() == null) {
            appUser.setIsActive(true);
        }
    }

    public void checkForDuplicateUserName(AppUser appUser) {
        try {
            AppUserDTO filter = new AppUserDTO();
            filter.setUserName(appUser.getUserName());
            List<AppUserDTO> duplicates = applicationContext.getBean(AppUserService.class).get(filter);
            duplicates.removeIf(x -> x.getId().equals(appUser.getId()));
            if (!duplicates.isEmpty()) {
                throw new CommonException(AppUserMessages.USER_NAME_DUPLICATE, appUser.getUserName());
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    public void checkForDuplicateEmail(AppUser appUser) {
        try {
            AppUserDTO filter = new AppUserDTO();
            filter.setEmail(appUser.getEmail());
            List<AppUserDTO> duplicates = applicationContext.getBean(AppUserService.class).get(filter);
            duplicates.removeIf(x -> x.getId().equals(appUser.getId()));
            if (!duplicates.isEmpty()) {
                throw new CommonException(AppUserMessages.EMAIL_DUPLICATE, appUser.getEmail());
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
