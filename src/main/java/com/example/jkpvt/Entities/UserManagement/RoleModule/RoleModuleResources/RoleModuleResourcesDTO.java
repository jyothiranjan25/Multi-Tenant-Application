package com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources;

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
