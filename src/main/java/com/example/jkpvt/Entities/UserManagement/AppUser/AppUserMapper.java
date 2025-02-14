package com.example.jkpvt.Entities.UserManagement.AppUser;

import com.example.jkpvt.Entities.UserManagement.AppUserRoles.AppUserRolesMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AppUserRolesMapper.class})
public interface AppUserMapper {

    @Mapping(target = "appUserRoles", source = "appUserRoles")
    AppUser map(AppUserDTO appUserDTO);

    @InheritConfiguration
    List<AppUserDTO> map(List<AppUser> appUserList);

    @InheritInverseConfiguration
    AppUserDTO map(AppUser appUser);
}
