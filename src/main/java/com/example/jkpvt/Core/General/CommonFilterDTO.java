package com.example.jkpvt.Core.General;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class CommonFilterDTO<T> {
    private Long id;
    private transient String modifiedGroup;
    private transient String modifiedBy;
    private transient String modifiedDate;
    private String searchTerm;
    private Integer pageOffset;
    private Integer pageSize;
    private Integer totalCount;
    private List<T> data;
}
