package com.example.jkpvt.Core.PaginationUtil;

import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;

public class PaginationUtil {

    /**
     * Applies pagination to a query based on DTO fields.
     * If page_offset or page_size are null, pagination is not applied.
     *
     * @param query The query to which pagination will be applied.
     * @param dto   The DTO containing pagination parameters.
     * @param <T>   The type of the query result.
     */
    public static <T> void applyPagination(TypedQuery<T> query, Object dto) {

        Integer pageOffset = getFieldValue(dto, "pageOffset");
        Integer pageSize = getFieldValue(dto, "pageSize");

        // Get the total count of the query results
        getTotalCount(query);

        // Apply pagination if both fields are provided
        if (pageOffset != null && pageSize != null) {
            // Adjust the pageOffset to be 0-based (since JPA uses 0-based index)
            if(pageOffset < 0)pageOffset = 0;
            else pageOffset = (pageOffset) * pageSize;

            // Apply pagination
            query.setFirstResult(pageOffset);  // Set the offset
            query.setMaxResults(pageSize);     // Set the limit
        }
    }

    private static Integer getFieldValue(Object dto, String fieldName) {
        try {
            Field field = dto.getClass().getDeclaredField(fieldName); // Access the fields using reflection and set them accessible
            field.setAccessible(true);  // Make private fields accessible
            return (Integer) field.get(dto); // Retrieve values from the fields
        } catch (Exception e) {
           return null;
        }
    }

    private static <T> void getTotalCount(TypedQuery<T> query) {
        // Get the total count of the query results
        query.getResultList();
        System.out.println("Total count: " + query.getResultList().size());
    }
}
