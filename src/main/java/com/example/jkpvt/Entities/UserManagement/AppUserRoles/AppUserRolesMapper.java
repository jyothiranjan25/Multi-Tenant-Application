package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Entities.UserManagement.AppUser.AppUserMapper;
import com.example.jkpvt.Entities.UserManagement.Roles.RolesMapper;
import com.example.jkpvt.Entities.UserManagement.UserGroup.UserGroupMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AppUserMapper.class, RolesMapper.class, UserGroupMapper.class})
public interface AppUserRolesMapper {

    @Mapping(target = "appUser.id", source = "appUserId")
    @Mapping(target = "roles.id", source = "rolesId")
    @Mapping(target = "userGroup.id", source = "userGroupId")
    AppUserRoles map(AppUserRolesDTO appUserRolesDTO);

    @InheritConfiguration
    List<AppUserRolesDTO> map(List<AppUserRoles> appUserRolesList);

    @InheritInverseConfiguration
    AppUserRolesDTO map(AppUserRoles appUserRoles);
}
