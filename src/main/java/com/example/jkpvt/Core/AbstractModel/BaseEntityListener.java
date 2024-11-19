package com.example.jkpvt.Core.AbstractModel;

import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class BaseEntityListener {

    @PrePersist
    public void prePersist(BaseAbstractModel<?> entity) {
        String userGroup = SessionStorageUtil.getUserGroup();
        String modifiedBy = SessionStorageUtil.getUserName();
        LocalDateTime modifiedDate = LocalDateTime.now();

        entity.setUserGroup(userGroup);
        entity.setModifiedBy(modifiedBy);
        entity.setModifiedDate(modifiedDate);
    }

    @PreUpdate
    public void PreUpdate(BaseAbstractModel<?> entity) {
        String modifiedBy = SessionStorageUtil.getUserName();
        LocalDateTime modifiedDate = LocalDateTime.now();

        entity.setModifiedBy(modifiedBy);
        entity.setModifiedDate(modifiedDate);
    }
}
