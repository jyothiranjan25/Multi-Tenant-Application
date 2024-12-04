package com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.Entities.UserManagement.Resources.Resources;
import com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleModuleResourcesService {

    private final RoleModuleResourcesRepository repository;
    private final RoleModuleResourcesDAO dao;
    private final RoleModuleResourcesMapper mapper;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<RoleModuleResourcesDTO> get(RoleModuleResourcesDTO dto) {
        return mapper.map(dao.get(dto));
    }

    @Transactional
    public void save(List<RoleModule> roleModules) {
        List<RoleModuleResources> resources = roleModules.stream()
                .flatMap(roleModule -> roleModule.getModule().getResources().stream()
                        .map(resource -> createRoleModuleResource(roleModule, resource)))
                .collect(Collectors.toList());

        repository.saveAll(resources);
    }

    private RoleModuleResources createRoleModuleResource(RoleModule roleModule, Resources resource) {
        RoleModuleResources roleModuleResources = new RoleModuleResources();
        roleModuleResources.setRole(roleModule.getRole());
        roleModuleResources.setModule(roleModule.getModule());
        roleModuleResources.setResource(resource);
        roleModuleResources.setIsVisible(resource.getShowInMenu());
        return roleModuleResources;
    }

    @Transactional
    public RoleModuleResourcesDTO update(RoleModuleResourcesDTO dto) {
        dto.setIsVisible(dto.getIsVisible() != null ? dto.getIsVisible() : true);

        RoleModuleResources roleModuleResource = dao.get(dto).stream()
                .findFirst()
                .orElseGet(() -> mapper.map(dto));

        roleModuleResource.setIsVisible(dto.getIsVisible());
        return mapper.map(repository.save(roleModuleResource));
    }


    @Transactional
    public void delete(List<RoleModule> roleModules) {
        List<RoleModuleResources> resources = roleModules.stream()
                .flatMap(roleModule -> roleModule.getModule().getResources().stream()
                        .map(resource -> createRoleModuleResource(roleModule, resource)))
                .toList();
        List<RoleModuleResources> roleModuleResources = repository.findByRoleInAndModuleInAndResourceIn(
                resources.stream().map(RoleModuleResources::getRole).toList(),
                resources.stream().map(RoleModuleResources::getModule).toList(),
                resources.stream().map(RoleModuleResources::getResource).toList()
        );
        repository.deleteAll(roleModuleResources);
    }
}
