package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModuleResourcesDTO {
    private Long id;
    private Long roleId;
    private Long moduleId;
    private Long resourceId;
    private Boolean isVisible;
}
