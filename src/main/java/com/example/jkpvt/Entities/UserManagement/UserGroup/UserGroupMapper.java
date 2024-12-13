package com.example.jkpvt.Entities.UserManagement.UserGroup;

import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserGroupMapper {

    UserGroup map(UserGroupDTO userGroupDTO);

    @InheritConfiguration(name = "map")
    List<UserGroupDTO> map(List<UserGroup> userGroupList);

    @InheritInverseConfiguration(name = "map")
    @Mapping(source = "parentGroup", target = "parentGroup", qualifiedByName = "mapParentGroup")
    @Mapping(source = "childGroups", target = "childGroups", qualifiedByName = "mapChildGroups")
    UserGroupDTO map(UserGroup userGroup);

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
}
