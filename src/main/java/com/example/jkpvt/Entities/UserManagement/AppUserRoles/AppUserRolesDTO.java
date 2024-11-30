package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.General.CommonFilterDTO;
import com.example.jkpvt.Entities.UserManagement.Roles.RolesDTO;
import com.example.jkpvt.Entities.UserManagement.UserGroup.UserGroupDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRolesDTO extends CommonFilterDTO<AppUserRolesDTO> {
    private RolesDTO roles;
    private UserGroupDTO userGroupDto;
    private Long appUserId;
    private Long rolesId;
}
