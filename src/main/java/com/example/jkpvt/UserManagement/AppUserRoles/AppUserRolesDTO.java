package com.example.jkpvt.UserManagement.AppUserRoles;

import com.example.jkpvt.UserManagement.Roles.RolesDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRolesDTO {
    private Long id;
    private RolesDTO roles;
    private String userGroup;
    private Long appUserId;
    private Long rolesId;
    private Integer pageOffset;
    private Integer pageSize;
}
