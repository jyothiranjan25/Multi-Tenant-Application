package com.example.jkpvt.Entities.UserManagement.Modules;

import com.example.jkpvt.Entities.SearchFilter.commonFilterDTO;
import com.example.jkpvt.Entities.UserManagement.Resources.ResourcesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModulesDTO extends commonFilterDTO {
    private Long id;
    private String moduleName;
    private String moduleDescription;
    private String moduleUrl;
    private String moduleIcon;
    private List<ResourcesDTO> resources;
    private List<Integer> resourceIds; // This is a list resource ids
    private Long moduleOrder;
}
