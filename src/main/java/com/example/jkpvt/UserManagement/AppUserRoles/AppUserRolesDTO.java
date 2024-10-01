package com.example.jkpvt.UserManagement.AppUserRoles;

import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import com.example.jkpvt.UserManagement.Roles.RolesDTO;
import lombok.*;

@Getter
@Setter
public class AppUserRolesDTO {
    private Long id;
    private AppUserDTO appUser;
    private RolesDTO roles;
    private String userGroup;
    private Long appUserId;
    private Long rolesId;
    private Integer pageOffset;
    private Integer pageSize;
}
