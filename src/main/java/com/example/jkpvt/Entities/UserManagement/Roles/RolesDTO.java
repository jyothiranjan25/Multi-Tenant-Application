package com.example.jkpvt.Entities.UserManagement.Roles;

import com.example.jkpvt.Core.General.CommonFilterDTO;
import com.example.jkpvt.Entities.UserManagement.Modules.ModulesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolesDTO extends CommonFilterDTO<RolesDTO> {
    private String roleName;
    private String roleDescription;
    private String roleIcon;
    private List<ModulesDTO> ModulesResources;
    private List<ModulesDTO> AddModules;
    private List<Long> removeModules;
}
