package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Modules.ModulesService;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.RoleModule.RoleModule;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleDTO;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources.RoleModuleResources;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesDAO dao;
    private final RolesMapper mapper;
    private final RolesRepository repository;
    private final RoleModuleService roleModuleService;
    private final ModulesService modulesService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<RolesDTO> get(RolesDTO rolesDTO) {
        List<Roles> roles = dao.get(rolesDTO);
        return mapToRolesDTO(roles);
    }

    @Transactional
    public RolesDTO create(RolesDTO rolesDTO) {
        try {
            Roles roles = mapper.map(rolesDTO);
            roles = repository.save(roles);
            RolesDTO rolesDTO1 = mapper.map(roles);
            roleModuleService.addOrRemove(setRoleModuleDTO(rolesDTO,roles));
            return rolesDTO1;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }


    @Transactional
    public RolesDTO update(RolesDTO rolesDTO) {
        try {
            Roles roles = getById(rolesDTO.getId());

            // Update the Roles entity with the new values
            if (rolesDTO.getRoleName() != null) {
                roles.setRoleName(rolesDTO.getRoleName());
            }
            if (rolesDTO.getRoleDescription() != null) {
                roles.setRoleDescription(rolesDTO.getRoleDescription());
            }
            if (rolesDTO.getRoleIcon() != null) {
                roles.setRoleIcon(rolesDTO.getRoleIcon());
            }
            roleModuleService.addOrRemove(setRoleModuleDTO(rolesDTO,roles));
            roles = repository.save(roles);
            return mapper.map(roles);
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
        return repository.findById(id).orElseThrow(() -> new CommonException("Role with id: " + id + " not found"));
    }

    private RoleModuleDTO setRoleModuleDTO(RolesDTO rolesDTO,Roles roles) {
        RoleModuleDTO roleModuleDTO = new RoleModuleDTO();
        roleModuleDTO.setRoleId(roles.getId());
        roleModuleDTO.setAddModules(rolesDTO.getAddModules());
        roleModuleDTO.setRemoveModules(rolesDTO.getRemoveModules());
        return roleModuleDTO;
    }

    public List<RolesDTO> mapToRolesDTO(List<Roles> roles) {
        List<RolesDTO> rolesDTOList = new ArrayList<>();
        for (Roles role : roles) {
            RolesDTO rolesDTO = mapper.map(role);
            List<Modules> modules = getModulesByRole(role);
            rolesDTO.setModulesResources(modulesService.MapToModelDto(modules, role.getId()));
            rolesDTOList.add(rolesDTO);
        }
        return rolesDTOList;
    }

    private List<Modules> getModulesByRole(Roles role) {
       Map<Long, Modules> moduleMap = new HashMap<>();

        if(!role.getRoleModuleResources().isEmpty()) {
            // group resources by module
            Map<Long, List<RoleModuleResources>> moduleResources = new HashMap<>();
            for (RoleModuleResources roleModuleResources : role.getRoleModuleResources()) {
                if (moduleResources.containsKey(roleModuleResources.getModule().getId())) {
                    moduleResources.get(roleModuleResources.getModule().getId()).add(roleModuleResources);
                } else {
                    List<RoleModuleResources> resources = new ArrayList<>();
                    resources.add(roleModuleResources);
                    moduleResources.put(roleModuleResources.getModule().getId(), resources);
                }
            }

            // create module object with resources
            for (Map.Entry<Long, List<RoleModuleResources>> entry : moduleResources.entrySet()) {
                Modules module = entry.getValue().get(0).getModule();
                Set<Resources> resources = new HashSet<>();
                for (RoleModuleResources roleModuleResources : entry.getValue()) {
                    if(!roleModuleResources.getIsVisible()){
                        continue;
                    }
                    resources.add(roleModuleResources.getResource());
                }
                module.setResources(resources);
                moduleMap.put(module.getId(), module);
            }
        }

        if (!role.getRoleModule().isEmpty()) {
            for (RoleModule roleModule : role.getRoleModule()) {
                if(!moduleMap.containsKey(roleModule.getModule().getId())) {
                    moduleMap.put(roleModule.getModule().getId(), roleModule.getModule());
                }
            }
        }
        return moduleMap.values().stream().toList();
    }
}
