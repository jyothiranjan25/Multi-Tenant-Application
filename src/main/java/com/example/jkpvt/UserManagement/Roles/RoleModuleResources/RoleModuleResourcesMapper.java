package com.example.jkpvt.UserManagement.Roles.RoleModuleResources;

import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.Modules.ModulesMapper;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;
import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import com.example.jkpvt.UserManagement.Roles.Roles;
import com.example.jkpvt.UserManagement.Roles.RolesDTO;
import com.example.jkpvt.UserManagement.Roles.RolesMapper;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ModulesMapper.class, ResourcesMapper.class})
public interface RoleModuleResourcesMapper {

    RoleModuleResourcesDTO map(RoleModuleResources roleModuleResources);

    @InheritConfiguration(name = "map")
    List<RoleModuleResourcesDTO> map(List<RoleModuleResources> roleModuleResources);

    @InheritInverseConfiguration(name = "map")
    RoleModuleResources map(RoleModuleResourcesDTO roleModuleResourcesDTO);
}
