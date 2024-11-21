package com.example.jkpvt.Core.PaginationUtil;

import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
            if (pageOffset < 0) pageOffset = 0;
            else pageOffset = (pageOffset) * pageSize;

            // Apply pagination
            query.setFirstResult(pageOffset);  // Set the offset
            query.setMaxResults(pageSize);     // Set the limit
        }

        // set Cacheable to true
        query.setHint("org.hibernate.cacheable", true);
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
//        System.out.println("Total count: " + query.getResultList().size());
    }

    public static <T> void addUserGroupFilter(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder) {
        String userGroup = SessionStorageUtil.getUserGroup();
        if (userGroup != null) {
            List<String> userGroups = getUserGroups(userGroup);

            // Create predicates for each user group and combine with OR
            List<Predicate> userGroupPredicates = new ArrayList<>();
            if(userGroups.size() > 1) {
                for (String group : userGroups) {
                    userGroupPredicates.add(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("userGroup")), group.toLowerCase())
                    );
                }
            }else {
                userGroupPredicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("userGroup")), userGroups.getFirst().toLowerCase()+"%")
                );
            }
            // Combine all predicates using CriteriaBuilder.or
            predicates.add(criteriaBuilder.or(userGroupPredicates.toArray(new Predicate[0])));
        }
    }

    private static List<String> getUserGroups(String userGroup) {
        List<String> userGroups = new ArrayList<>();
        String[] parts = userGroup.split("\\.");
        StringBuilder current = new StringBuilder();
        for (String part : parts) {
            if (!current.isEmpty()) {
                current.append(".");
            }
            current.append(part);
            userGroups.add(current.toString());
        }
        return userGroups;
    }
}
