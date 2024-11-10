package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.UserManagement.Modules.ModulesMapper;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources.RoleModuleResourcesMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ModulesMapper.class, RoleModuleResourcesMapper.class})
public interface RoleModuleMapper {

    @Mapping(source = "module.id", target = "moduleId")
    RoleModuleDTO map(RoleModule roleModule);

    @InheritConfiguration(name = "map")
    List<RoleModuleDTO> map(List<RoleModule> roleModule);

    @InheritInverseConfiguration(name = "map")
    @Mapping(source = "roleId", target = "role.id")
    RoleModule map(RoleModuleDTO roleModuleDTO);
}
