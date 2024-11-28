package com.example.jkpvt.Core.General;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class CommonFilterDTO<T> {
    private Long id;
    private String userGroup;
    private String modifiedBy;
    private String modifiedDate;
    private String searchTerm;
    private Integer pageOffset;
    private Integer pageSize;
    private Integer totalCount;
    private List<T> data;
    private List<OrderByDTO> orderDetails;


    public enum OrderType {
        asc("asc"),
        desc("desc");

        @Getter
        @Setter
        private String value;

        OrderType(String value) {
            this.value = value;
        }
    }
}
