package com.example.jkpvt.UserManagement.Resources;

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
public class ResourcesListener implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ResourcesListener.applicationContext = applicationContext;
    }

    @PrePersist
    public void prePersist(Resources resources) {
        conditionsChecks(resources);
        checkDuplicateData(resources);
        checkForParentData(resources);
    }

    @PreUpdate
    public void preUpdate(Resources resources) {
        conditionsChecks(resources);
        checkDuplicateData(resources);
    }

    @PreRemove
    public void preRemove(Resources resources) {
        if (resources.getId() == null) {
            throw new CommonException("Resource ID is mandatory");
        }
        checkForLinkedData(resources);
    }

    public void conditionsChecks(Resources resources) {
        if (resources.getResourceName() == null || resources.getResourceName().isEmpty()) {
            throw new CommonException("Resource name is mandatory");
        }
        if (resources.getShowInMenu() == null) {
            resources.setShowInMenu(true);
        }
        if (resources.getResourceFullName() == null || resources.getResourceFullName().isEmpty()) {
            resources.setResourceFullName(resources.getResourceName());
        }
        if (resources.getParentResource() == null) {
            if (resources.getResourceUrl() == null || resources.getResourceUrl().equals("#")) {
                resources.setResourceUrl("#");
            } else {
                throw new CommonException("Parent ID is required");
            }
        } else {
            if (resources.getResourceUrl() == null || resources.getResourceUrl().isEmpty()) {
                throw new CommonException("URL is required");
            }
            if ("#".equals(resources.getResourceUrl())) {
                throw new CommonException("URL is not valid");
            }
        }
    }

    public void checkDuplicateData(Resources resources) {
        try {
            ResourcesDTO filter = new ResourcesDTO();
            filter.setResourceName(resources.getResourceName());
            List<ResourcesDTO> resourcesList = applicationContext.getBean(ResourcesService.class).get(filter);
            resourcesList.removeIf(x -> x.getId().equals(resources.getId()));
            if (!resourcesList.isEmpty()) {
                throw new CommonException("Resource name already exists");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    public void checkForLinkedData(Resources resources) {
        try {
            ResourcesDTO filter = new ResourcesDTO();
            filter.setParentId(resources.getId());
            List<ResourcesDTO> children = applicationContext.getBean(ResourcesService.class).get(filter);
            if (!children.isEmpty()) {
                throw new CommonException("Resource has child records");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    public void checkForParentData(Resources resources) {
        try {
            Resources data = applicationContext.getBean(ResourcesService.class).getById(resources.getParentResource().getId());
            if(data.getParentResource() != null) {
                throw new CommonException("Resource has Parent record");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
