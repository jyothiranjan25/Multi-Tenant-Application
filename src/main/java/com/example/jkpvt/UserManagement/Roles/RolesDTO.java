package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.Roles.RoleModuleResources.RoleModuleResourcesDTO;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RolesDTO {
    private Long id;
    private String roleName;
    private String roleDescription;
    private String roleIcon;
    private List<ModulesDTO> modules;
    private List<ModulesDTO> moduleResources;
    private Integer pageOffset;
    private Integer pageSize;
}
