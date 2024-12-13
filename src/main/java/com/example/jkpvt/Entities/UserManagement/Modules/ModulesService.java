package com.example.jkpvt.Entities.UserManagement.Modules;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.Messages.CommonMessages;
import com.example.jkpvt.Core.Messages.Messages;
import com.example.jkpvt.Entities.UserManagement.Resources.Resources;
import com.example.jkpvt.Entities.UserManagement.Resources.ResourcesService;
import com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModulesService {

    private final ModulesDAO modulesDAO;
    private final ModulesMapper mapper;
    private final ModulesRepository modulesRepository;
    private final ResourcesService resourcesService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<ModulesDTO> get(ModulesDTO modulesDTO) {
        List<Modules> modules = modulesDAO.get(modulesDTO);
        return MapToModelDto(modules);
    }

    @Transactional
    public ModulesDTO create(ModulesDTO modulesDTO) {
        Modules modules = mapper.map(modulesDTO);
        setResources(modules, modulesDTO);
        modulesRepository.save(modules);
        return mapper.map(modules);
    }

    @Transactional
    public ModulesDTO update(ModulesDTO modulesDTO) {
        Modules modules = getById(modulesDTO.getId());
        updateModules(modules, modulesDTO);
        modulesRepository.saveAndFlush(modules);
        return mapper.map(modules);
    }

    @Transactional
    public String delete(ModulesDTO modulesDTO) {
        Modules module = getById(modulesDTO.getId());

        // Clear relationships in the join table
        for (Resources resource : new HashSet<>(module.getResources())) {
            resource.getModules().remove(module);
        }
        module.getResources().clear();
        modulesRepository.save(module); // Save the module to persist the relationship removal

        // Delete the module itself
        modulesRepository.delete(module);

        return Messages.getMessage(CommonMessages.DATA_DELETE_SUCCESS).getMessage();
    }

    private Set<Resources> setResources(ModulesDTO modulesDTO) {
        List<Long> resourceIds = modulesDTO.getResourceIds().stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<Resources> resources = resourcesService.getByIds(resourceIds);
        return new HashSet<>(resources);
    }

    @Transactional(readOnly = true)
    public Modules getById(Long id) {
        return modulesRepository.findById(id)
                .orElseThrow(() -> new CommonException(ModulesMessages.MODULE_NOT_FOUND));
    }

    private void updateModules(Modules modules, ModulesDTO modulesDTO) {
        setResources(modules, modulesDTO);
        if (modulesDTO.getModuleName() != null) {
            modules.setModuleName(modulesDTO.getModuleName());
        }
        if (modulesDTO.getModuleDescription() != null) {
            modules.setModuleDescription(modulesDTO.getModuleDescription());
        }
        if (modulesDTO.getModuleUrl() != null) {
            modules.setModuleUrl(modulesDTO.getModuleUrl());
        }
    }

    private void setResources(Modules modules, ModulesDTO modulesDTO) {
        if (modulesDTO.getResourceIds() != null) {
            Set<Resources> resources = setResources(modulesDTO);
            modules.setResources(resources);
        }
    }

    public List<ModulesDTO> MapToModelDto(List<Modules> modules) {
        List<ModulesDTO> modulesDTOS = new ArrayList<>();
        for (Modules module : modules) {
            ModulesDTO modulesDTO = mapper.map(module);
            modulesDTO.setResources(resourcesService.getResourceDtoList(new ArrayList<>(module.getResources())));
            modulesDTOS.add(modulesDTO);
        }
        return modulesDTOS;
    }

    public List<ModulesDTO> MapToModelDto(List<Modules> modules, Long roleId) {
        List<ModulesDTO> modulesDTOS = new ArrayList<>();
        for (Modules module : modules) {
            ModulesDTO modulesDTO = mapper.map(module);
            Optional<RoleModule> roleModuleOptional = module.getRoleModule().stream()
                    .filter(roleModule -> roleModule.getRole().getId().equals(roleId))
                    .findFirst();
            roleModuleOptional.ifPresent(roleModule -> modulesDTO.setModuleOrder(roleModule.getModuleOrder()));
            modulesDTO.setResources(resourcesService.getResourceDtoList(new ArrayList<>(module.getResources())));
            modulesDTOS.add(modulesDTO);
        }
        return modulesDTOS;
    }
}