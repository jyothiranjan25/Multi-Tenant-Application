package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources.RoleModuleResourcesDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class RoleModuleDTO {
    private Long id;
    private Long roleId;
    private Long moduleId;
    private ModulesDTO module;
    private List<RoleModuleResourcesDTO> roleModuleResources;
    private int modelOrder;
}
