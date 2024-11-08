package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class RolesDTO {
    private Long id;
    private String roleName;
    private String roleDescription;
    private String roleIcon;
    private List<ModulesDTO> modules;
    private List<RoleModuleDTO> roleModules;
    private Integer pageOffset;
    private Integer pageSize;
}
