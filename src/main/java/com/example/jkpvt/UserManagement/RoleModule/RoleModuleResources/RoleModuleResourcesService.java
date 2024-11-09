package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.Modules.ModulesService;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;
import com.example.jkpvt.UserManagement.Resources.ResourcesService;
import com.example.jkpvt.UserManagement.RoleModule.RoleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleModuleResourcesService {

    private final RoleModuleResourcesRepository repository;
    private final RoleModuleResourcesMapper mapper;
    private final ModulesService modulesService;

    @Transactional
    public List<RoleModuleResourcesDTO> saveAll(List<RoleModule> roleModules) {
        List<RoleModuleResources> roleModuleResources = new ArrayList<>();
        for (RoleModule roleModule : roleModules) {
            Modules module = modulesService.getById(roleModule.getModule().getId());
           if(module.getResources() != null) {
               for (Resources resource : module.getResources()) {
                   RoleModuleResources roleModuleResource = new RoleModuleResources();
                   roleModuleResource.setRoleModule(roleModule);
                   roleModuleResource.setResource(resource);
                   roleModuleResource.setVisible(resource.getShowInMenu());
                   roleModuleResources.add(roleModuleResource);
               }
           }
        }
        roleModuleResources = repository.saveAll(roleModuleResources);
        return mapper.map(roleModuleResources);
    }
}
