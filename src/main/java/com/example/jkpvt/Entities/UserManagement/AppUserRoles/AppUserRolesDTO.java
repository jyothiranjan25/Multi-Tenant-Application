package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Entities.SearchFilter.commonFilterDTO;
import com.example.jkpvt.Entities.UserManagement.Roles.RolesDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRolesDTO extends commonFilterDTO {
    private Long id;
    private RolesDTO roles;
    private String userGroup;
    private Long appUserId;
    private Long rolesId;
}
