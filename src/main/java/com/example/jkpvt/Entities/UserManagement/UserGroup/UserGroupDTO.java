package com.example.jkpvt.Entities.UserManagement.UserGroup;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserGroupDTO {
    private Long id;
    private String groupName;
    private String groupDescription;
    private String qualifiedName;
    private Long parentId;
    private UserGroupDTO parentGroup;
    private Set<UserGroupDTO> childGroups;
    private Integer pageOffset;
    private Integer pageSize;
}
