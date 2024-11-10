package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.Modules.ModulesService;
import com.example.jkpvt.UserManagement.RoleModule.*;
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
    private final ModulesService modulesService;
    private final RoleModuleService roleModuleService;

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public List<RolesDTO> get(RolesDTO rolesDTO) {
        List<Roles> roles = dao.get(rolesDTO);
        return mapper.map(roles);
    }

    @Transactional
    public RolesDTO create(RolesDTO rolesDTO) {
        try {
            Roles roles = mapper.map(rolesDTO);
            roles = repository.save(roles);
            RolesDTO newRolesDTO = mapper.map(roles);
            newRolesDTO.setRoleModules(saveRoleModules(rolesDTO, roles));
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
        return repository.findById(id).orElseThrow(()->new CommonException("Role with id: "+ id +" not found"));
    }

    private List<RoleModuleDTO> saveRoleModules(RolesDTO rolesDTO, Roles roles) {
        if(rolesDTO.getModules() != null) {
            List<RoleModule> roleModules = new ArrayList<>();
            for (ModulesDTO modulesDTO : rolesDTO.getModules()) {
                RoleModule roleModule = new RoleModule();
                roleModule.setRole(roles);
                roleModule.setModule(modulesService.getById(modulesDTO.getId()));
                roleModule.setModelOrder(modulesDTO.getModelOrder());
                roleModules.add(roleModule);
            }
            return roleModuleService.saveAll(roleModules);
        }else{
            throw new CommonException("Modules cannot be null");
        }
    }
}
