package com.example.jkpvt.UserManagement.UserGroup;

import org.mapstruct.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserGroupMapper {

    @Mapping(source = "parentGroup", target = "parentGroup", qualifiedByName = "mapParentGroup")
    @Mapping(source = "childGroups", target = "childGroups", qualifiedByName = "mapChildGroups")
    UserGroupDTO map(UserGroup userGroup);

    @InheritConfiguration(name = "map")
    List<UserGroupDTO> map(List<UserGroup> userGroupList);

    @InheritInverseConfiguration(name = "map")
    @Mapping(source = "parentGroup", target = "parentGroup", qualifiedByName = "mapParentGroupDTO")
    @Mapping(source = "childGroups", target = "childGroups", qualifiedByName = "mapChildGroupsDTO")
    UserGroup map(UserGroupDTO userGroupDTO);

    @Named("mapParentGroup")
    default UserGroupDTO mapParentGroup(UserGroup parentGroup) {
        if (parentGroup == null) {
            return null;
        }
        UserGroupDTO dto = new UserGroupDTO();
        dto.setId(parentGroup.getId());
        dto.setGroupName(parentGroup.getGroupName());
        dto.setGroupDescription(parentGroup.getGroupDescription());
        dto.setQualifiedName(parentGroup.getQualifiedName());
        // Avoid infinite loop by not setting parentGroup or childGroups
        return dto;
    }

    @Named("mapParentGroupDTO")
    default UserGroup mapParentGroupDTO(UserGroupDTO parentGroupDTO) {
        if (parentGroupDTO == null) {
            return null;
        }
        UserGroup entity = new UserGroup();
        entity.setId(parentGroupDTO.getId());
        entity.setGroupName(parentGroupDTO.getGroupName());
        entity.setGroupDescription(parentGroupDTO.getGroupDescription());
        entity.setQualifiedName(parentGroupDTO.getQualifiedName());
        // Avoid infinite loop by not setting parentGroup or childGroups
        return entity;
    }

    @Named("mapChildGroups")
    default Set<UserGroupDTO> mapChildGroups(Set<UserGroup> childGroups) {
        if (childGroups == null || childGroups.isEmpty()) {
            return null;
        }
        return childGroups.stream()
                .map(childGroup -> {
                    UserGroupDTO dto = new UserGroupDTO();
                    dto.setId(childGroup.getId());
                    dto.setGroupName(childGroup.getGroupName());
                    dto.setGroupDescription(childGroup.getGroupDescription());
                    dto.setQualifiedName(childGroup.getQualifiedName());
                    // Avoid infinite loop by not setting parentGroup or childGroups
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapChildGroupsDTO")
    default Set<UserGroup> mapChildGroupsDTO(Set<UserGroupDTO> childGroupsDTO) {
        if (childGroupsDTO == null) {
            return null;
        }
        return childGroupsDTO.stream()
                .map(childGroupDTO -> {
                    UserGroup entity = new UserGroup();
                    entity.setId(childGroupDTO.getId());
                    entity.setGroupName(childGroupDTO.getGroupName());
                    entity.setGroupDescription(childGroupDTO.getGroupDescription());
                    entity.setQualifiedName(childGroupDTO.getQualifiedName());
                    // Avoid infinite loop by not setting parentGroup or childGroups
                    return entity;
                })
                .collect(Collectors.toSet());
    }
}
