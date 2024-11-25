package com.example.jkpvt.Entities.SearchFilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class commonFilterDTO {
    private String searchTerm;
    private Integer pageOffset;
    private Integer pageSize;
    private Integer totalRecords;
}
