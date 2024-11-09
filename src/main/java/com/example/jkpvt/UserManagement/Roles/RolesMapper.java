package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.UserManagement.RoleModule.RoleModuleMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleModuleMapper.class})
public interface RolesMapper {

    @Mapping(target = "roleModules", source = "roleModule")
    RolesDTO map(Roles roles);

    @InheritConfiguration(name = "map")
    List<RolesDTO> map(List<Roles> roles);

    @InheritInverseConfiguration(name = "map")
    Roles map(RolesDTO rolesDTO);
}
