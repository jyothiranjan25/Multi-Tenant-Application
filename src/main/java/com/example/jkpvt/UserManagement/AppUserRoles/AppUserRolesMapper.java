package com.example.jkpvt.UserManagement.AppUserRoles;

import com.example.jkpvt.UserManagement.AppUser.AppUserMapper;
import com.example.jkpvt.UserManagement.Roles.RolesMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AppUserMapper.class, RolesMapper.class})
public interface AppUserRolesMapper {

    @Mapping(target = "appUserId", source = "appUser.id")
    @Mapping(target = "roles", source = "roles")
    AppUserRolesDTO map(AppUserRoles appUserRoles);

    @InheritConfiguration
    List<AppUserRolesDTO> map(List<AppUserRoles> appUserRolesList);

    @InheritInverseConfiguration
    @Mapping(target = "roles", source = "roles")
    AppUserRoles map(AppUserRolesDTO appUserRolesDTO);
}
