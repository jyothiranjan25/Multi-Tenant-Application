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
    @Mapping(target = "parentResource", ignore = true)
    @Mapping(target = "childResources", ignore = true)
    Resources map(ResourcesDTO resourcesDTO);

    @Named("mapResourcesWithoutParent")
    @Mapping(target = "parentResource", ignore = true) // Ignore parentResource in this case
    @Mapping(source = "childResources", target = "childResources", qualifiedByName = "mapChild")
    ResourcesDTO mapWithoutParent(Resources resources);

    @Named("mapResourcesWithoutParentAndChild")
    @Mapping(target = "parentResource", ignore = true) // Ignore parentResource in this case
    @Mapping(target = "childResources", ignore = true)
    ResourcesDTO mapResources(Resources resources);

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
}
