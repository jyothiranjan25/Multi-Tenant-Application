package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.UserManagement.Roles.RoleModuleResources.RoleModuleResourcesMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleModuleResourcesMapper.class})
public interface RolesMapper {

    RolesDTO map(Roles roles);

    @InheritConfiguration(name = "map")
    List<RolesDTO> map(List<Roles> roles);

    @InheritInverseConfiguration(name = "map")
    Roles map(RolesDTO rolesDTO);
}
