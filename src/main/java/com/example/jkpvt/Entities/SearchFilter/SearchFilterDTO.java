package com.example.jkpvt.Entities.SearchFilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFilterDTO {
    private String searchTerm;
    private Long pageOffset;
    private Long pageSize;
}
