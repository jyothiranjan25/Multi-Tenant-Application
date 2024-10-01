package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ResourcesMapper.class)
public interface ModulesMapper {

    // Mapping for Modules where we ignore the parentResource field in resources
    @Mapping(target = "resources", qualifiedByName = "mapResourcesWithoutParent")
    ModulesDTO map(Modules modules);

    @InheritConfiguration(name = "map")
    List<ModulesDTO> map(List<Modules> modulesList);

    @InheritInverseConfiguration(name = "map")
    @Mapping(target = "resources", qualifiedByName = "mapResourcesWithoutParentDTO")
    Modules map(ModulesDTO modulesDTO);
}
