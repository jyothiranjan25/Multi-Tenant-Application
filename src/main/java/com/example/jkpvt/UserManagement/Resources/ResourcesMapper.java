package com.example.jkpvt.UserManagement.Resources;

import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ResourcesMapper {

    @Mapping(source = "parentResource", target = "parentResource", qualifiedByName = "mapParent")
    @Mapping(source = "childResources", target = "childResources", qualifiedByName = "mapChild")
    ResourcesDTO map(Resources resources);

    @InheritConfiguration(name = "map")
    List<ResourcesDTO> map(List<Resources> ResourcesList);

    @InheritInverseConfiguration(name = "map")
    @Mapping(source = "parentResource", target = "parentResource", qualifiedByName = "mapParentDTO")
    @Mapping(source = "childResources", target = "childResources", qualifiedByName = "mapChildDTO")
    Resources map(ResourcesDTO resourcesDTO);

    @Named("mapResourcesWithoutParent")
    @Mapping(target = "parentResource", ignore = true) // Ignore parentResource in this case
    @Mapping(source = "childResources", target = "childResources", qualifiedByName = "mapChild")
    ResourcesDTO mapWithoutParent(Resources resources);

    @InheritInverseConfiguration(name = "map")
    @Named("mapResourcesWithoutParentDTO")
    @Mapping(target = "parentResource", ignore = true) // Ignore parentResource in this case
    @Mapping(source = "childResources", target = "childResources", qualifiedByName = "mapChildDTO")
    Resources mapResourcesWithoutParentDTO(ResourcesDTO resourcesDTO);

    @Named("mapParent")
    default ResourcesDTO mapParent(Resources parentResource) {
        if (parentResource == null) {
            return null;
        }
        ResourcesDTO dto = new ResourcesDTO();
        dto.setId(parentResource.getId());
        dto.setResourceName(parentResource.getResourceName());
        dto.setResourceFullName(parentResource.getResourceFullName());
        dto.setResourceDescription(parentResource.getResourceDescription());
        dto.setResourceUrl(parentResource.getResourceUrl());
        dto.setShowInMenu(parentResource.getShowInMenu());
        dto.setResourceOrder(parentResource.getResourceOrder());
        dto.setResourceSubOrder(parentResource.getResourceSubOrder());
        dto.setParentId(parentResource.getParentResource()!=null?parentResource.getParentResource().getId():null);
        return dto;
    }

    @Named("mapParentDTO")
    default Resources mapParentDTO(ResourcesDTO parentResourceDTO) {
        if (parentResourceDTO == null) {
            return null;
        }
        Resources entity = new Resources();
        entity.setId(parentResourceDTO.getId());
        entity.setResourceName(parentResourceDTO.getResourceName());
        entity.setResourceFullName(parentResourceDTO.getResourceFullName());
        entity.setResourceDescription(parentResourceDTO.getResourceDescription());
        entity.setResourceUrl(parentResourceDTO.getResourceUrl());
        entity.setResourceOrder(parentResourceDTO.getResourceOrder());
        entity.setResourceSubOrder(parentResourceDTO.getResourceSubOrder());
        entity.setShowInMenu(parentResourceDTO.getShowInMenu());
        return entity;
    }

    @Named("mapChild")
    default Set<ResourcesDTO> mapChild(Set<Resources> childResources) {
        if (childResources == null || childResources.isEmpty()) {
            return null;
        }
        return childResources.stream()
                .map(childResource -> {
                    ResourcesDTO dto = new ResourcesDTO();
                    dto.setId(childResource.getId());
                    dto.setResourceName(childResource.getResourceName());
                    dto.setResourceFullName(childResource.getResourceFullName());
                    dto.setResourceDescription(childResource.getResourceDescription());
                    dto.setResourceUrl(childResource.getResourceUrl());
                    dto.setShowInMenu(childResource.getShowInMenu());
                    dto.setResourceOrder(childResource.getResourceOrder());
                    dto.setResourceSubOrder(childResource.getResourceSubOrder());
                    dto.setParentId(childResource.getParentResource() != null?childResource.getParentResource().getId():null);
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapChildDTO")
    default Set<Resources> mapChildDTO(Set<ResourcesDTO> childResourcesDTO) {
        if (childResourcesDTO == null || childResourcesDTO.isEmpty()) {
            return null;
        }
        return childResourcesDTO.stream()
                .map(childResourceDTO -> {
                    Resources entity = new Resources();
                    entity.setId(childResourceDTO.getId());
                    entity.setResourceName(childResourceDTO.getResourceName());
                    entity.setResourceFullName(childResourceDTO.getResourceFullName());
                    entity.setResourceDescription(childResourceDTO.getResourceDescription());
                    entity.setResourceUrl(childResourceDTO.getResourceUrl());
                    entity.setResourceOrder(childResourceDTO.getResourceOrder());
                    entity.setResourceSubOrder(childResourceDTO.getResourceSubOrder());
                    entity.setShowInMenu(childResourceDTO.getShowInMenu());
                    return entity;
                })
                .collect(Collectors.toSet());
    }
}
