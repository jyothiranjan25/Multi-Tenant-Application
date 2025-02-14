package com.example.jkpvt.Entities.UserManagement.RoleModule;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Entities.UserManagement.Modules.Modules;
import com.example.jkpvt.Entities.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.Entities.UserManagement.Modules.ModulesService;
import com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources.RoleModuleResourcesService;
import com.example.jkpvt.Entities.UserManagement.Roles.RolesMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleModuleService {

    private final RoleModuleRepository repository;
    private final RoleModuleMapper mapper;
    private final ModulesService modulesService;
    private final RoleModuleDAO dao;
    private final RoleModuleResourcesService roleModuleResourcesService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<RoleModuleDTO> get(RoleModuleDTO roleModuleDTO) {
        List<RoleModule> roleModules = dao.get(roleModuleDTO);
        return mapper.map(roleModules);
    }

    @Transactional
    public List<RoleModuleDTO> addOrRemove(RoleModuleDTO roleModuleDTO) {
        List<RoleModule> roleModules = new ArrayList<>();

        if (roleModuleDTO.getAddModules() != null) {
            roleModules.addAll(handleAddModules(roleModuleDTO));
        }

        if (roleModuleDTO.getRemoveModules() != null) {
            handleRemoveModules(roleModuleDTO.getRoleId(), roleModuleDTO.getRemoveModules());
        }

        return mapper.map(roleModules);
    }

    private List<RoleModule> handleAddModules(RoleModuleDTO roleModuleDTO) {
        List<RoleModule> addList = new ArrayList<>();
        List<RoleModule> updateList = new ArrayList<>();

        for (ModulesDTO module : roleModuleDTO.getAddModules()) {
            RoleModule existingRoleModule = getByRoleAndModuleId(roleModuleDTO.getRoleId(), module.getId());

            if (existingRoleModule == null) {
                addList.add(createNewRoleModule(roleModuleDTO, module));
            } else {
                if (module.getModuleOrder() != null) {
                    existingRoleModule.setModuleOrder(module.getModuleOrder());
                }
                updateList.add(existingRoleModule);
            }
        }

        List<RoleModule> savedModules = new ArrayList<>();
        if (!addList.isEmpty()) {
            roleModuleResourcesService.save(addList);
            savedModules.addAll(repository.saveAll(addList));
        }
        if (!updateList.isEmpty()) savedModules.addAll(repository.saveAll(updateList));
        return savedModules;
    }

    private RoleModule createNewRoleModule(RoleModuleDTO roleModuleDTO, ModulesDTO moduleDTO) {
        RoleModule newRoleModule = mapper.map(roleModuleDTO);
        Modules module = modulesService.getById(moduleDTO.getId());
        newRoleModule.setModule(module);
        newRoleModule.setModuleOrder(moduleDTO.getModuleOrder());
        return newRoleModule;
    }

    private void handleRemoveModules(Long roleId, List<Long> moduleIdsToRemove) {
        List<RoleModule> allRoleModules = getByRoleId(roleId);
        List<RoleModule> modulesToRemove = allRoleModules.stream()
                .filter(roleModule -> moduleIdsToRemove.contains(roleModule.getModule().getId()))
                .toList();
        if (allRoleModules.size() == modulesToRemove.size()) {
            throw new CommonException(RolesMessages.ROLE_MODULE_MANDATORY);
        } else if (!modulesToRemove.isEmpty()) {
            roleModuleResourcesService.delete(modulesToRemove);
            repository.deleteAll(modulesToRemove);
        }
    }

    public RoleModule getByRoleAndModuleId(Long roleId, Long moduleId) {
        Optional<RoleModule> roleModule = repository.findByRoleIdAndModuleId(roleId, moduleId);
        return roleModule.orElse(null);
    }

    public List<RoleModule> getByRoleId(Long roleId) {
        return repository.findByRoleId(roleId);
    }
}
