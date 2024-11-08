package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModuleResourcesDTO {
    private Long id;
    private RoleModuleDTO roleModule;
    private ResourcesDTO resource;
    private boolean isVisible;
}
