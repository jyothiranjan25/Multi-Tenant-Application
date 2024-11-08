package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import com.example.jkpvt.UserManagement.RoleModule.RoleModule;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ResourcesMapper.class})
public interface RoleModuleResourcesMapper {
    RoleModuleResourcesDTO map(RoleModuleResources roleModuleResources);

    @InheritConfiguration(name = "map")
    List<RoleModuleResourcesDTO> map(List<RoleModuleResources> roleModuleResources);

    @InheritInverseConfiguration(name = "map")
    RoleModuleResources map(RoleModuleResourcesDTO roleModuleResourcesDTO);
}
