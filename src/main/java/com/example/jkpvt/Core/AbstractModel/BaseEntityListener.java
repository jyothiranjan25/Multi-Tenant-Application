package com.example.jkpvt.Core.AbstractModel;

import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class BaseEntityListener {

    @PrePersist
    public void prePersist(BaseAbstractModel entity) {
        String userGroup = SessionStorageUtil.getUserGroup();
        String modifiedBy = SessionStorageUtil.getUserName();

        entity.setUserGroup(userGroup);
        entity.setModifiedBy(modifiedBy);
    }

    @PreUpdate
    public void PreUpdate(BaseAbstractModel entity) {
        String modifiedBy = SessionStorageUtil.getUserName();
        entity.setModifiedBy(modifiedBy);
    }
}
