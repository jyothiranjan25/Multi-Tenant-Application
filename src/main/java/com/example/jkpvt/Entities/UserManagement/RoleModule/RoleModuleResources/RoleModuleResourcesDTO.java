package com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.Core.General.CommonFilterDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModuleResourcesDTO extends CommonFilterDTO {
    private Long id;
    private Long roleId;
    private Long moduleId;
    private Long resourceId;
    private Boolean isVisible;
}
