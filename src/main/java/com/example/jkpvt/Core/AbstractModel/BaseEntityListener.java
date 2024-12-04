package com.example.jkpvt.Core.AbstractModel;

import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import com.example.jkpvt.Entities.UserManagement.UserGroup.UserGroupDTO;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class BaseEntityListener {

    @PrePersist
    public void prePersist(BaseAbstractModel entity) {
        UserGroupDTO userGroup = SessionStorageUtil.getUserGroup();
        String modifiedBy = SessionStorageUtil.getUserName();

        entity.setUserGroup(userGroup.getQualifiedName());
        entity.setModifiedBy(modifiedBy);
    }

    @PreUpdate
    public void PreUpdate(BaseAbstractModel entity) {
        String modifiedBy = SessionStorageUtil.getUserName();
        entity.setModifiedBy(modifiedBy);
    }
}
