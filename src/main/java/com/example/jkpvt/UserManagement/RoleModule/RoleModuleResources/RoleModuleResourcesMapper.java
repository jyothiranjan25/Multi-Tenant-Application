package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;
import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import com.example.jkpvt.UserManagement.RoleModule.RoleModule;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = ResourcesMapper.class)
public interface RoleModuleResourcesMapper {

    @Mapping(target = "resource", qualifiedByName = "mapResourcesWithoutParentAndChild")
    RoleModuleResourcesDTO map(RoleModuleResources roleModuleResources);

    @InheritConfiguration(name = "map")
    List<RoleModuleResourcesDTO> map(List<RoleModuleResources> roleModuleResources);

    @InheritInverseConfiguration(name = "map")
    @Mapping(target = "resource", ignore = true)
    RoleModuleResources map(RoleModuleResourcesDTO roleModuleResourcesDTO);
}
