package com.example.jkpvt.UserManagement.UserGroup;

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
public class UserGroupListener implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserGroupListener.applicationContext = applicationContext;
    }

    @PrePersist
    public void prePersist(UserGroup userGroup) {
        if (userGroup.getGroupName() == null || userGroup.getGroupName().isEmpty()) {
            throw new CommonException("name is mandatory");
        }
        checkForDuplicateGroupName(userGroup);
    }

    @PreUpdate
    public void preUpdate(UserGroup userGroup) {
        if (userGroup.getId() == null) {
            throw new CommonException("ID is mandatory");
        }
        checkForDuplicateGroupName(userGroup);
    }

    @PreRemove
    public void preRemove(UserGroup userGroup) {
        if (userGroup.getId() == null) {
            throw new CommonException("ID is mandatory");
        }
        checkForLinkedData(userGroup);
    }

    private void checkForDuplicateGroupName(UserGroup userGroup) {
        try {
            UserGroupDTO filter = new UserGroupDTO();
            filter.setGroupName(userGroup.getGroupName());
            List<UserGroupDTO> duplicates = applicationContext.getBean(UserGroupService.class).get(filter);
            duplicates.removeIf(x -> x.getId().equals(userGroup.getId()));
            if (!duplicates.isEmpty()) {
                throw new CommonException("Group name already exists");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private void checkForLinkedData(UserGroup userGroup) {
        try {
            UserGroupDTO filter = new UserGroupDTO();
            filter.setParentId(userGroup.getId());
            List<UserGroupDTO> children = applicationContext.getBean(UserGroupService.class).get(filter);
            if (!children.isEmpty()) {
                throw new CommonException("Group has child records");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
