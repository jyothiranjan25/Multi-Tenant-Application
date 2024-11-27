package com.example.jkpvt.Core.General;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class CommonFilterDTO<T> {
    private String searchTerm;
    private Integer pageOffset;
    private Integer pageSize;
    private Integer totalCount;
    private List<T> data;
}
