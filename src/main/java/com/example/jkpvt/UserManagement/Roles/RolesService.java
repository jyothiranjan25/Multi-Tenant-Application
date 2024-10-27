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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public Roles getById(Long id) {
        return repository.findById(id).orElseThrow(()->new CommonException("Role with id: "+ id +" not found"));
    }
}
