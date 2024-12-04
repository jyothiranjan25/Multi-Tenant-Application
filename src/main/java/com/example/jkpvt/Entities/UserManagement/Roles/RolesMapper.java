package com.example.jkpvt.Entities.UserManagement.Roles;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolesMapper {

    Roles map(RolesDTO rolesDTO);

    @InheritConfiguration(name = "map")
    List<RolesDTO> map(List<Roles> roles);

    @InheritInverseConfiguration(name = "map")
    RolesDTO map(Roles roles);
}
