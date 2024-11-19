package com.example.jkpvt.Core.AbstractModel;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@MappedSuperclass
public class AbstractModel<T> {

    @Transient
    private List<T> listResult;

    @Transient
    private T result;

    @Transient
    private Long totalCount;

    @Transient
    private Long pageSize;

    @Transient
    private Long pageOffset;

    @Transient
    private Boolean isUpdate;
}
