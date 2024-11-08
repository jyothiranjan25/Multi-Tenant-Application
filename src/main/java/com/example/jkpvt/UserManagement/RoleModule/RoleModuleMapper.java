package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.UserManagement.Modules.ModulesMapper;
import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import com.example.jkpvt.UserManagement.Roles.Roles;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ModulesMapper.class, Roles.class})
public interface RoleModuleMapper {

    @Mapping(source = "module", target = "modules")
    RoleModuleDTO map(RoleModule roleModuleResources);

    @InheritConfiguration(name = "map")
    List<RoleModuleDTO> map(List<RoleModule> roleModuleResources);

    @InheritInverseConfiguration(name = "map")
    @Mapping(source = "roleId", target = "role.id")
    RoleModule map(RoleModuleDTO roleModuleResourcesDTO);
}
