package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleModuleDTO {
    private Long id;
    private Long roleId;
    private Long moduleId;
    private ModulesDTO module;
    private List<ResourcesDTO> resources;
    private int modelOrder;
}
