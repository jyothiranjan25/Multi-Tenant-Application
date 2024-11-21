package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.UserManagement.Modules.ModulesMapper;
import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import com.example.jkpvt.UserManagement.Roles.RolesMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RolesMapper.class, ModulesMapper.class, ResourcesMapper.class})
public interface RoleModuleResourcesMapper {

    @Mapping(target = "role.id", source = "roleId")
    @Mapping(target = "module.id", source = "moduleId")
    @Mapping(target = "resource.id", source = "resourceId")
    RoleModuleResources map(RoleModuleResourcesDTO roleModuleResourcesDTO);

    @InheritConfiguration
    List<RoleModuleResourcesDTO> map(List<RoleModuleResources> roleModuleResources);

    @InheritInverseConfiguration
    RoleModuleResourcesDTO map(RoleModuleResources roleModuleResources);
}