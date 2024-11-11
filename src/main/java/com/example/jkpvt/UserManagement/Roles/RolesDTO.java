package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.UserManagement.RoleModule.RoleModuleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolesDTO {
    private Long id;
    private String roleName;
    private String roleDescription;
    private String roleIcon;
    private List<RoleModuleDTO> roleModules;
    private Integer pageOffset;
    private Integer pageSize;
}
