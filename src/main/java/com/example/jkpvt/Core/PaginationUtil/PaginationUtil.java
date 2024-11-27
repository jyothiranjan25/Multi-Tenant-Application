package com.example.jkpvt.Core.PaginationUtil;

import com.example.jkpvt.Core.General.CommonFilterDTO;
import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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
    public static <T> void applyPagination(TypedQuery<T> query, CommonFilterDTO dto) {
        // Get the total count of the query results
        getTotalCount(query, dto);

        // Apply pagination if both fields are provided
        if (dto.getPageOffset() != null && dto.getPageSize() != null) {
            int pageOffset = dto.getPageOffset();
            int pageSize = dto.getPageSize();
            // Adjust the pageOffset to be 0-based (since JPA uses 0-based index)
            if (pageOffset < 0) pageOffset = 0;
            else pageOffset = (pageOffset) * pageSize;

            // Apply pagination
            query.setFirstResult(pageOffset);  // Set the offset
            query.setMaxResults(pageSize);     // Set the limit
        }

        // set Cacheable to true
        query.setHint("org.hibernate.cacheable", true);
        /*
         * Cache mode options:
         * NORMAL: The default mode. Hibernate checks the cache for the data first, and if it doesn't find it, it will hit the database.
         * IGNORE: Hibernate will ignore the cache and hit the database directly.
         * GET: Hibernate will hit the cache directly and if it doesn't find the data, it will hit the database.
         * PUT: Hibernate will put the data in the cache.
         * REFRESH: Hibernate will hit the database and refresh the cache.
         */
        query.setHint("org.hibernate.cacheMode", "NORMAL");
//        query.setHint("org.hibernate.readOnly", true);
    }

    private static <T> void getTotalCount(TypedQuery<T> query, CommonFilterDTO dto) {
        // Get the total count of the query results
        Integer totalCount = query.getResultList().size();
        dto.setTotalCount(totalCount);
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
