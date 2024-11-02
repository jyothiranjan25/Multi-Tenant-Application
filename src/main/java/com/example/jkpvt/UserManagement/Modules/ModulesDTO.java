package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.UserManagement.Resources.ResourcesDTO;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ModulesDTO {
    private Long id;
    private String moduleName;
    private String moduleDescription;
    private String moduleUrl;
    private String moduleIcon;
    private List<ResourcesDTO> resources;
    private List<Integer> resourceIds; // This is a list resource ids
    private Integer modelOrder;
    private Integer pageOffset;
    private Integer pageSize;
}
