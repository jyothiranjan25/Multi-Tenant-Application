package com.example.jkpvt.Entities.UserManagement.AppUser;

import com.example.jkpvt.Entities.UserManagement.AppUserRoles.AppUserRolesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AppUserDTO {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private Boolean isAdmin;
    private Boolean isActive;
    private List<AppUserRolesDTO> appUserRoles;
    private Integer pageOffset;
    private Integer pageSize;
}
