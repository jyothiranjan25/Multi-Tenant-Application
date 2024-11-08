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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesDAO dao;
    private final RolesMapper mapper;
    private final RolesRepository repository;
    private final ModulesService modulesService;
    private final ResourcesService resourcesService;

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public List<RolesDTO> get(RolesDTO rolesDTO) {
        List<Roles> roles = dao.get(rolesDTO);
        List<RolesDTO> rolesDTOList = new ArrayList<>();
        for (Roles role : roles) {
            RolesDTO newRoleDTO = mapper.map(role);
            newRoleDTO.setModuleResources(mapToModulesDTO(role));
            rolesDTOList.add(newRoleDTO);
        }
        return rolesDTOList;
    }

    @Transactional
    public RolesDTO create(RolesDTO rolesDTO) {
        try {
            Roles roles = mapper.map(rolesDTO);

            Set<RoleModuleResources> roleModuleResources = setRoleModuleResources(roles, rolesDTO);
            roles.setRoleModuleResources(roleModuleResources);

            roles = repository.save(roles);
            RolesDTO newRolesDTO = mapper.map(roles);
            newRolesDTO.setModuleResources(mapToModulesDTO(roles));
            return newRolesDTO;
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
            RolesDTO newRolesDTO = mapper.map(roles);
            newRolesDTO.setModuleResources(mapToModulesDTO(roles));
            return newRolesDTO;
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

    public List<ModulesDTO> mapToModulesDTO(Roles roles) {
        Map<Long, List<RoleModuleResources>> roleModuleResources = groupRoleModuleResourcesByModule(roles);
        List<Modules> modules = new ArrayList<>();
        for (Map.Entry<Long, List<RoleModuleResources>> entry : roleModuleResources.entrySet()) {
            Modules newModule = entry.getValue().getFirst().getModule();
            newModule.setResources(entry.getValue().stream().map(RoleModuleResources::getResource).collect(Collectors.toSet()));
            modules.add(newModule);
        }
        return modulesService.MapToModelDto(modules);
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
}
