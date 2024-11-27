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
    private OrderType orderType;
    private List<CommonFilterDTO> notIn;
    private List<Long> idIn;
    private String orderProperty;
    private Boolean nToManyFiltersExist = true;

    public enum OrderType {
        asc("asc"),
        desc("desc");

        private String value;

        OrderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
