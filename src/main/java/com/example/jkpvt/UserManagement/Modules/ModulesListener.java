package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.Resources.Resources;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class ModulesListener implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ModulesListener.applicationContext = applicationContext;
    }

    @PrePersist
    public void prePersist(Modules modules) {
        if (modules.getModuleName() == null || modules.getModuleName().isEmpty()) {
            throw new CommonException("Modules name is mandatory");
        }
        checkForDuplicateGroupName(modules);
    }

    @PreUpdate
    public void preUpdate(Modules modules) {
        checkForDuplicateGroupName(modules);
    }

    @PreRemove
    public void preRemove(Modules modules) {
        // checkIDExists(modules);
    }

    private void conditionsChecks(ModulesDTO modulesDTO, Modules modules, Set<Resources> resources) {
        if (modulesDTO.getModuleUrl() == null && resources.isEmpty()) {
            throw new CommonException("Module URL is required");
        }
        if (modulesDTO.getModuleUrl() != null && !resources.isEmpty()) {
            modules.setModuleUrl(null);
        }
        // check parent and child resources
        checkParentHasChild(resources);
    }

    private void checkParentHasChild(Set<Resources> resources) {
        Set<Long> parentIds = new HashSet<>();
        Set<Long> childIds = new HashSet<>();

        for (Resources resources1 : resources) {
            if (resources1.getParentResource() == null) {
                parentIds.add(resources1.getId());
            } else {
                childIds.add(resources1.getParentResource().getId());
            }
        }

        // check parent has child
        for (Long parentId : parentIds) {
            boolean hasChilds = resources.stream().anyMatch(resource ->
                    Objects.equals(resource.getParentResource() != null ? resource.getParentResource().getId() : null, parentId));
            if (!hasChilds) {
                throw new CommonException("Parent resource should have child resource");
            }
        }

        // check child has parent
        for (Long childId : childIds) {
            boolean hasParent = resources.stream().anyMatch(resource ->
                    Objects.equals(resource.getId(), childId));
            if (!hasParent) {
                throw new CommonException("Child resource should have parent resource");
            }
        }
    }

    private void checkForDuplicateGroupName(Modules modules) {
        try {
            ModulesDTO filter = new ModulesDTO();
            filter.setModuleName(modules.getModuleName());
            List<ModulesDTO> duplicates = applicationContext.getBean(ModulesService.class).get(filter);
            duplicates.removeIf(x -> x.getId().equals(modules.getId()));
            if (!duplicates.isEmpty()) {
                throw new CommonException("Model name already exists");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
