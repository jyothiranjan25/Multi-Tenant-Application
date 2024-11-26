package com.example.jkpvt.Core.PaginationUtil;

public class ThreadLocalUtil {
    private static final ThreadLocal<Long> totalRecords = new ThreadLocal<>();

    public static void setTotalRecords(Long value) {
        totalRecords.set(value);
    }

    private static Long getTotalRecords() {
        return totalRecords.get();
    }

    private static void clearTotalRecords() {
        totalRecords.remove();
    }

    public static Long getTotalRecordsAndClear() {
        try{
            return getTotalRecords();
        }finally {
            clearTotalRecords();
        }
    }

}
