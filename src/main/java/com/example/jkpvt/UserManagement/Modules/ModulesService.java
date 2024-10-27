package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Resources.ResourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ModulesService {

    private final ModulesDAO modulesDAO;
    private final ModulesMapper mapper;
    private final ModulesRepository modulesRepository;
    private final ResourcesService resourcesService;

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public List<ModulesDTO> get(ModulesDTO modulesDTO) {
        List<Modules> modules = modulesDAO.get(modulesDTO);
        return mapper.map(modules);
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
            if (modulesRepository.existsById(modulesDTO.getId())) {
                modulesRepository.deleteById(modulesDTO.getId());
                return "Data deleted successfully";
            } else {
                throw new CommonException("Data not found");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    public Set<Resources> setResources(ModulesDTO modulesDTO) {
        Set<Resources> resources = new HashSet<>();
        for (Integer resourceId : modulesDTO.getResourceIds()) {
            Resources resources1 = resourcesService.getById((long)resourceId);
            resources.add(resources1);
        }
        return resources;
    }

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public Modules getById(Long id) {
        return modulesRepository.findById(id)
                .orElseThrow(() -> new CommonException("Module id not found"));
    }
}
