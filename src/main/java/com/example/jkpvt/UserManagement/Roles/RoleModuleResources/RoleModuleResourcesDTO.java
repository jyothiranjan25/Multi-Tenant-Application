package com.example.jkpvt.UserManagement.Roles.RoleModuleResources;

import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class RoleModuleResourcesDTO {
    private Long id;
    private ModulesDTO module;
    private ResourcesDTO resource;
    private List<ModulesDTO> modules;
    private int modelOrder;
    private boolean ShowInMenu;
}
