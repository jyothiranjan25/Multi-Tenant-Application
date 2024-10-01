package com.example.jkpvt.UserManagement.AppUserRoles;

import com.example.jkpvt.UserManagement.AppUser.AppUser;
import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import com.example.jkpvt.UserManagement.AppUser.AppUserMapper;
import com.example.jkpvt.UserManagement.Roles.RolesMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AppUserMapper.class, RolesMapper.class})
public interface AppUserRolesMapper {

    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "mapAppUser")
    @Mapping(target = "roles", source = "roles")
    AppUserRolesDTO map(AppUserRoles appUserRoles);

    @InheritConfiguration
    List<AppUserRolesDTO> map(List<AppUserRoles> appUserRolesList);

    @InheritInverseConfiguration
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "mapAppUserDTO")
    @Mapping(target = "roles", source = "roles")
    AppUserRoles map(AppUserRolesDTO appUserRolesDTO);

    @Named("mapAppUser")
    default AppUserDTO mapAppUser(AppUser appUser) {
        if (appUser == null) {
            return null;
        }
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(appUser.getId());
        appUserDTO.setUserName(appUser.getUserName());
        appUserDTO.setPassword(appUser.getPassword());
        appUserDTO.setEmail(appUser.getEmail());
        appUserDTO.setIsAdmin(appUser.getIsAdmin());
        appUserDTO.setIsActive(appUser.getIsActive());
        appUserDTO.setAppUserRoles(null);
        return appUserDTO;
    }

    @Named("mapAppUserDTO")
    default AppUser mapAppUserDTO(AppUserDTO appUserDTO) {
        if (appUserDTO == null) {
            return null;
        }
        AppUser appUser = new AppUser();
        appUser.setId(appUserDTO.getId());
        appUser.setUserName(appUserDTO.getUserName());
        appUser.setPassword(appUserDTO.getPassword());
        appUser.setEmail(appUserDTO.getEmail());
        appUser.setIsAdmin(appUserDTO.getIsAdmin());
        appUser.setIsActive(appUserDTO.getIsActive());
        appUser.setAppUserRoles(null);
        return appUser;
    }
}
