package com.example.jkpvt.Core.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseAbstractModel {

    @Column(name = "user_group")
    private String userGroup;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Version
    @Column(name="modified_date")
    private LocalDateTime modifiedDate;
}
