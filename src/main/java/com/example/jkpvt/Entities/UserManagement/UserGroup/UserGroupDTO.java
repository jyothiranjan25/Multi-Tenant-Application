package com.example.jkpvt.Entities.UserManagement.UserGroup;


import com.example.jkpvt.Entities.SearchFilter.commonFilterDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserGroupDTO extends commonFilterDTO {
    private Long id;
    private String groupName;
    private String groupDescription;
    private String qualifiedName;
    private Long parentId;
    private UserGroupDTO parentGroup;
    private Set<UserGroupDTO> childGroups;
}
