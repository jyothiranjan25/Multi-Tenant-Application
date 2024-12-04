package com.example.jkpvt.Entities.UserManagement.UserGroup;


import com.example.jkpvt.Core.General.CommonFilterDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserGroupDTO extends CommonFilterDTO<UserGroupDTO> {
    private String groupName;
    private String groupDescription;
    private String qualifiedName;
    private Long parentId;
    private UserGroupDTO parentGroup;
    private Set<UserGroupDTO> childGroups;
}
