package com.example.jkpvt.Entities.UserManagement.Resources;

import com.example.jkpvt.Core.General.CommonFilterDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ResourcesDTO extends CommonFilterDTO {
    private Long id;
    private String resourceName;
    private String resourceFullName;
    private String resourceDescription;
    private String resourceUrl;
    private Boolean showInMenu;
    private Long resourceOrder;
    private String resourceSubOrder;
    private Long parentId;
    private ResourcesDTO parentResource;
    private Set<ResourcesDTO> childResources;
}
