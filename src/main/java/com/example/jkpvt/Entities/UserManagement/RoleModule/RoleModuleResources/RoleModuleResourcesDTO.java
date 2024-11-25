package com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.Entities.SearchFilter.commonFilterDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModuleResourcesDTO extends commonFilterDTO {
    private Long id;
    private Long roleId;
    private Long moduleId;
    private Long resourceId;
    private Boolean isVisible;
}
