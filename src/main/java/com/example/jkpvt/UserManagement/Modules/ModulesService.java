package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Resources.ResourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        try {
            Modules modules = mapper.map(modulesDTO);

            if (modulesDTO.getResourceIds() != null) {
                Set<Resources> resources = setResources(modulesDTO);
                modules.setResources(resources);
            }

            modulesRepository.save(modules);
            return mapper.map(modules);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public ModulesDTO update(ModulesDTO modulesDTO) {
        try {
            Modules modules = getById(modulesDTO.getId());

            if (modulesDTO.getModuleName() != null) {
                modules.setModuleName(modulesDTO.getModuleName());
            }
            if (modulesDTO.getModuleDescription() != null) {
                modules.setModuleDescription(modulesDTO.getModuleDescription());
            }
            if (modulesDTO.getModuleUrl() != null) {
                modules.setModuleUrl(modulesDTO.getModuleUrl());
            }

            if (modulesDTO.getResourceIds() != null) {
                Set<Resources> resources = setResources(modulesDTO);
                modules.setResources(resources);
            }

            modulesRepository.save(modules);
            return mapper.map(modules);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String delete(ModulesDTO modulesDTO) {
        try {
            Modules module = getById(modulesDTO.getId());

            // Clear relationships in the join table
            for (Resources resource : new HashSet<>(module.getResources())) {
                resource.getModules().remove(module);
            }
            module.getResources().clear();
            modulesRepository.save(module); // Save the module to persist the relationship removal

            // Delete the module itself
            modulesRepository.delete(module);

            return "Module deleted successfully";
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    public Set<Resources> setResources(ModulesDTO modulesDTO) {
        List<Long> resourceIds = modulesDTO.getResourceIds().stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<Resources> resources = resourcesService.getByIds(resourceIds);
        return new HashSet<>(resources);
    }

    @Transactional(readOnly = true)
    public Modules getById(Long id) {
        return modulesRepository.findById(id)
                .orElseThrow(() -> new CommonException("Module id not found"));
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
}
