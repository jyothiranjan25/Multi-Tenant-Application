package com.example.jkpvt.Entities.UserManagement.Roles;

import com.example.jkpvt.Entities.SearchFilter.commonFilterDTO;
import com.example.jkpvt.Entities.UserManagement.Modules.ModulesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolesDTO extends commonFilterDTO {
    private Long id;
    private String roleName;
    private String roleDescription;
    private String roleIcon;
    private List<ModulesDTO> ModulesResources;
    private List<ModulesDTO> AddModules;
    private List<Long> removeModules;
}
