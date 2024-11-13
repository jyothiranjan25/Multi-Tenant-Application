package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.UserManagement.Modules.ModulesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleModuleDTO {
    private Long id;
    private Long roleId;
    private Long moduleId;
    private List<ModulesDTO> AddModules;
    private List<Long> removeModules;
    private Long modelOrder;
}
