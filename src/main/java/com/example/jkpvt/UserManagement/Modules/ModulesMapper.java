package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.UserManagement.Resources.ResourcesMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = ResourcesMapper.class)
public interface ModulesMapper {

    Modules map(ModulesDTO modulesDTO);

    @InheritConfiguration(name = "map")
    List<ModulesDTO> map(List<Modules> modulesList);

    @InheritInverseConfiguration(name = "map")
    @Mapping(target = "resources", qualifiedByName = "mapResourcesWithoutParent")
    ModulesDTO map(Modules modules);


    @Named("mapModulesWithoutResources")
    @Mapping(target = "resources", qualifiedByName = "mapResourcesWithoutParentAndChild")
    ModulesDTO mapWithoutResources(Modules modules);

}
