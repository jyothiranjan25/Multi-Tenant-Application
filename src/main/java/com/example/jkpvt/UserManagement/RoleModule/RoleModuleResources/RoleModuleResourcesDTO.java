package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModuleResourcesDTO {
    private Long id;
    private ResourcesDTO resource;
    private boolean isVisible;
}
