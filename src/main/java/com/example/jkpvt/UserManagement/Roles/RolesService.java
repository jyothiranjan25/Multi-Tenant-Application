package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.Modules.ModulesService;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;
import com.example.jkpvt.UserManagement.Resources.ResourcesService;
import com.example.jkpvt.UserManagement.Roles.RoleModuleResources.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesDAO dao;
    private final RolesMapper roleMapper;
    private final RolesRepository repository;
    private final ModulesService modulesService;
    private final ResourcesService resourcesService;

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public List<RolesDTO> get(RolesDTO rolesDTO) {
        List<Roles> roles = dao.get(rolesDTO);
        return roleMapper.map(roles);
    }

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public List<RolesDTO> getRoles(RolesDTO rolesDTO) {
        List<Roles> roles = dao.get(rolesDTO);
        List<RolesDTO> rolesDTOList = new ArrayList<>();
        for (Roles role : roles) {
            RolesDTO newRoleDTO = mapToRolesDTO(role);
            newRoleDTO.setModuleResources(mapToModulesDTO(role));
            rolesDTOList.add(newRoleDTO);
        }
        return rolesDTOList;
    }

    @Transactional
    public RolesDTO create(RolesDTO rolesDTO) {
        try {
            Roles roles = roleMapper.map(rolesDTO);

            Set<RoleModuleResources> roleModuleResources = setRoleModuleResources(roles, rolesDTO);
            roles.setRoleModuleResources(roleModuleResources);

            roles = repository.save(roles);
            return roleMapper.map(roles);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }


    @Transactional
    public RolesDTO update(RolesDTO rolesDTO) {
        try {
            Roles roles = getById(rolesDTO.getId());

            // Update the Roles entity with the new values
            if(rolesDTO.getRoleName() != null) {
                roles.setRoleName(rolesDTO.getRoleName());
            }
            if(rolesDTO.getRoleDescription() != null) {
                roles.setRoleDescription(rolesDTO.getRoleDescription());
            }
            if(rolesDTO.getRoleIcon() != null) {
                roles.setRoleIcon(rolesDTO.getRoleIcon());
            }
            if (rolesDTO.getModules() != null) {
                Set<RoleModuleResources> roleModuleResources = setRoleModuleResources(roles, rolesDTO);
                roles.getRoleModuleResources().clear();
                roles.getRoleModuleResources().addAll(roleModuleResources);
            }

            roles = repository.save(roles);
            return roleMapper.map(roles);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String delete(RolesDTO rolesDTO) {
        try {
            if (repository.existsById(rolesDTO.getId())) {
                repository.deleteById(rolesDTO.getId());
                return "Data deleted successfully";
            } else {
                throw new CommonException("Data not found");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Roles getById(Long id) {
        return repository.findById(id).orElseThrow(()->new CommonException("Role with id: "+ id +" not found"));
    }

    public Set<RoleModuleResources> setRoleModuleResources(Roles roles, RolesDTO rolesDTO) {
        // Create a Set for RoleModuleResources if it doesn't exist
        Set<RoleModuleResources> roleModuleResources = new HashSet<>();

        // Loop through the modules from the DTO
        if (rolesDTO.getModules() != null) {
            for (ModulesDTO moduleDTO : rolesDTO.getModules()) {
                Modules modules = modulesService.getById(moduleDTO.getId());
                int order = moduleDTO.getModelOrder();
                for (ResourcesDTO resource : moduleDTO.getResources()) {
                    // get resources
                    Resources resources = resourcesService.getById(resource.getId());

                    RoleModuleResources roleModuleResource = new RoleModuleResources();
                    roleModuleResource.setRole(roles);
                    roleModuleResource.setModule(modules);
                    roleModuleResource.setResource(resources);
                    roleModuleResource.setModelOrder(order);
                    roleModuleResource.setShowInMenu(resource.getShowInMenu());
                    roleModuleResources.add(roleModuleResource);
                }
            }
        }
        return roleModuleResources;
    }

    public RolesDTO mapToRolesDTO(Roles roles) {
        RolesDTO rolesDTO = new RolesDTO();
        rolesDTO.setId(roles.getId());
        rolesDTO.setRoleName(roles.getRoleName());
        rolesDTO.setRoleDescription(roles.getRoleDescription());
        rolesDTO.setRoleIcon(roles.getRoleIcon());
        return rolesDTO;
    }

    public Set<ModulesDTO> mapToModulesDTO(Roles roles) {
        Map<Long, List<RoleModuleResources>> roleModuleResources = groupRoleModuleResourcesByModule(roles);
        Set<ModulesDTO> modulesDTOSet = new HashSet<>();

        for (Map.Entry<Long, List<RoleModuleResources>> entry : roleModuleResources.entrySet()) {
            ModulesDTO modulesDTO = createModulesDTO(entry);
            modulesDTOSet.add(modulesDTO);
        }

        return modulesDTOSet;
    }

    private Map<Long, List<RoleModuleResources>> groupRoleModuleResourcesByModule(Roles roles) {
        Map<Long, List<RoleModuleResources>> roleModuleResources = new HashMap<>();
        for (RoleModuleResources roleModuleResource : roles.getRoleModuleResources()) {
            roleModuleResources
                    .computeIfAbsent(roleModuleResource.getModule().getId(), k -> new ArrayList<>())
                    .add(roleModuleResource);
        }
        return roleModuleResources;
    }

    private ModulesDTO createModulesDTO(Map.Entry<Long, List<RoleModuleResources>> entry) {
        ModulesDTO modulesDTO = new ModulesDTO();
        modulesDTO.setId(entry.getKey());

        Modules module = entry.getValue().getFirst().getModule();
        modulesDTO.setModuleName(module.getModuleName());
        modulesDTO.setModuleDescription(module.getModuleDescription());
        modulesDTO.setModuleUrl(module.getModuleUrl());
        modulesDTO.setModelOrder(entry.getValue().getFirst().getModelOrder());
        modulesDTO.setResources(new ArrayList<>());

        Map<Long, List<Resources>> childResources = groupChildResources(entry.getValue());
        addResourcesToModulesDTO(entry.getValue(), modulesDTO, childResources);

        return modulesDTO;
    }

    private Map<Long, List<Resources>> groupChildResources(List<RoleModuleResources> roleModuleResources) {
        Map<Long, List<Resources>> childResources = new HashMap<>();
        for (RoleModuleResources roleModuleResource : roleModuleResources) {
            if (roleModuleResource.getResource().getParentResource() != null) {
                childResources
                        .computeIfAbsent(roleModuleResource.getResource().getParentResource().getId(), k -> new ArrayList<>())
                        .add(roleModuleResource.getResource());
            }
        }
        return childResources;
    }

    private void addResourcesToModulesDTO(List<RoleModuleResources> roleModuleResources, ModulesDTO modulesDTO, Map<Long, List<Resources>> childResources) {
        for (RoleModuleResources roleModuleResource : roleModuleResources) {
            if (roleModuleResource.getResource().getParentResource() != null) {
                continue;
            }
            ResourcesDTO resourcesDTO = createResourcesDTO(roleModuleResource, childResources);
            modulesDTO.getResources().add(resourcesDTO);
        }
    }

    private ResourcesDTO createResourcesDTO(RoleModuleResources roleModuleResource, Map<Long, List<Resources>> childResources) {
        ResourcesDTO resourcesDTO = new ResourcesDTO();
        resourcesDTO.setId(roleModuleResource.getResource().getId());
        resourcesDTO.setResourceName(roleModuleResource.getResource().getResourceName());
        resourcesDTO.setResourceDescription(roleModuleResource.getResource().getResourceDescription());
        resourcesDTO.setResourceUrl(roleModuleResource.getResource().getResourceUrl());
        resourcesDTO.setResourceOrder(roleModuleResource.getResource().getResourceOrder());
        resourcesDTO.setShowInMenu(roleModuleResource.getResource().getShowInMenu());
        resourcesDTO.setChildResources(mapToResourcesDTO(childResources));
        return resourcesDTO;
    }

    public Set<ResourcesDTO> mapToResourcesDTO(Map<Long, List<Resources>> childResources) {
        Set<ResourcesDTO> resourcesDTOSet = new HashSet<>();
        for (Map.Entry<Long, List<Resources>> entry : childResources.entrySet()) {
            for (Resources resource : entry.getValue()) {
                ResourcesDTO resourcesDTO = new ResourcesDTO();
                resourcesDTO.setId(resource.getId());
                resourcesDTO.setResourceName(resource.getResourceName());
                resourcesDTO.setResourceDescription(resource.getResourceDescription());
                resourcesDTO.setResourceUrl(resource.getResourceUrl());
                resourcesDTO.setResourceOrder(resource.getResourceOrder());
                resourcesDTO.setShowInMenu(resource.getShowInMenu());
                resourcesDTO.setResourceOrder(Long.parseLong(resource.getResourceSubOrder()));
                resourcesDTOSet.add(resourcesDTO);
            }
        }
        return resourcesDTOSet;
    }
}
