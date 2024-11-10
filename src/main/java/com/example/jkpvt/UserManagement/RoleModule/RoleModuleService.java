package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.Resources.Resources;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleModuleService {

    private final RoleModuleRepository repository;
    private final RoleModuleMapper mapper;

    @Transactional
    public List<RoleModuleDTO> saveAll(List<RoleModule> roleModules) {
        try {
            List<RoleModule> roleModuleList = roleModules;
            for (RoleModule roleModule : roleModuleList) {
                if (roleModule.getModule() != null && roleModule.getModule().getResources() != null) {
                    Set<Resources> newResources = new HashSet<>();
                    for (Resources resource : roleModule.getModule().getResources()) {
                        if (resource.getShowInMenu()) {
                            newResources.add(resource);
                        }
                    }
                    roleModule.setResources(newResources);
                }
            }
            roleModuleList = repository.saveAll(roleModuleList);
            return mapper.map(roleModuleList);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

}
