package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.UserManagement.Modules.ModulesMapper;
import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ModulesMapper.class, ResourcesMapper.class})
public interface RoleModuleMapper {

    @Mapping(target = "module", qualifiedByName = "mapModulesWithoutResources")
    @Mapping(target = "resources", qualifiedByName = "mapResourcesWithoutParentAndChild")
    RoleModuleDTO map(RoleModule roleModule);

    @InheritConfiguration(name = "map")
    List<RoleModuleDTO> map(List<RoleModule> roleModule);

    @InheritInverseConfiguration(name = "map")
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "module", ignore = true)
    RoleModule map(RoleModuleDTO roleModuleDTO);
}
