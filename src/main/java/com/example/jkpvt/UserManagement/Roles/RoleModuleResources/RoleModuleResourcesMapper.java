package com.example.jkpvt.UserManagement.Roles.RoleModuleResources;

import com.example.jkpvt.UserManagement.Modules.ModulesMapper;
import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ModulesMapper.class, ResourcesMapper.class})
public interface RoleModuleResourcesMapper {

    RoleModuleResourcesDTO map(RoleModuleResources roleModuleResources);

    @InheritConfiguration(name = "map")
    List<RoleModuleResourcesDTO> map(List<RoleModuleResources> roleModuleResources);

    @InheritInverseConfiguration(name = "map")
    RoleModuleResources map(RoleModuleResourcesDTO roleModuleResourcesDTO);
}
