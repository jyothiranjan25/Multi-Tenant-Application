package com.example.jkpvt.Entities.UserManagement.RoleModule;

import com.example.jkpvt.Entities.SearchFilter.commonFilterDTO;
import com.example.jkpvt.Entities.UserManagement.Modules.ModulesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleModuleDTO extends commonFilterDTO {
    private Long id;
    private Long roleId;
    private Long moduleId;
    private List<ModulesDTO> AddModules;
    private List<Long> removeModules;
    private Long moduleOrder;
}
