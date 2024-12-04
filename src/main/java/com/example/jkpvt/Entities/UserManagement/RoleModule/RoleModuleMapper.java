package com.example.jkpvt.Entities.UserManagement.RoleModule;

import com.example.jkpvt.Entities.UserManagement.Modules.ModulesMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ModulesMapper.class})
public interface RoleModuleMapper {

    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "moduleId", target = "module.id")
    RoleModule map(RoleModuleDTO roleModuleDTO);

    @InheritConfiguration(name = "map")
    List<RoleModuleDTO> map(List<RoleModule> roleModule);

    @InheritInverseConfiguration(name = "map")
    RoleModuleDTO map(RoleModule roleModule);
}
