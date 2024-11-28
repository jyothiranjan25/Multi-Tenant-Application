package com.example.jkpvt.Core.General.CriteriaBuilder;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.General.CommonFilterDTO;
import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CriteriaBuilderWrapper<T> {

    private final Class<T> entityClass;
    private final Session session;
    private final CommonFilterDTO filter;
    private final HibernateCriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<T> criteriaQuery;
    private final Root<T> root;
    private Predicate finalPredicate;

    /**
     * Initializes the CriteriaBuilderWrapper with the entity class, session, and filter data.
     *
     * @param entityClass The class type of the entity.
     * @param session     The Hibernate session used for query execution.
     * @param filter      The filter DTO containing pagination and filtering criteria.
     */
    public CriteriaBuilderWrapper(Class<T> entityClass, Session session, CommonFilterDTO filter) {
        this.entityClass = entityClass;
        this.session = session;
        this.filter = filter;
        this.criteriaBuilder = session.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        this.root = criteriaQuery.from(entityClass);
        this.finalPredicate = criteriaBuilder.conjunction();
    }

    /**
     * Constructs and returns the final query with applied filters, pagination, and cache hints.
     *
     * @return A Query object ready for execution.
     */
    private Query getFinalQuery() {
        addUserGroupFilter();
        criteriaQuery.where(finalPredicate);
        Query query = session.createQuery(criteriaQuery);
        addPaginationFilters(filter, query);
        addHibernateFilters(query);
        return query;
    }

    /**
     * Executes the query and returns the result list.
     *
     * @return A list of entities matching the query.
     */
    public List<T> getResultList() {
        List<T> result = getFinalQuery().getResultList();
        filter.setTotalCount(result.size());
        return result;
    }

    /**
     * Adds an equal condition to the query.
     *
     * @param key   The field name.
     * @param value The value to match.
     */
    public void Equal(String key, Object value) {
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get(key), value));
    }

    /**
     * Adds a "not equal" condition to the query.
     */
    public void NotEqual(String key, Object value) {
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.notEqual(root.get(key), value));
    }

    /**
     * Adds a "like" condition to the query for partial matching.
     */
    public void Like(String key, String value) {
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.like(root.get(key), value));
    }

    /**
     * Adds a case-insensitive "like" condition to the query.
     */
    public void ILike(String key, String value) {
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.ilike(root.get(key), value));
    }

    /**
     * Adds an "IN" condition for a list of objects.
     */
    public void InObjects(String key, List<Object> values) {
        finalPredicate = criteriaBuilder.and(finalPredicate, root.get(key).in(values));
    }

    /**
     * Adds an "IN" condition for a list of strings.
     */
    public void InString(String key, List<String> values) {
        finalPredicate = criteriaBuilder.and(finalPredicate, root.get(key).in(values));
    }

    /**
     * Applies pagination filters (offset and limit) to the query.
     *
     * @param filter The filter DTO containing pagination details.
     * @param query  The query object to modify.
     */
    public void addPaginationFilters(CommonFilterDTO filter, Query query) {
        if (filter.getPageOffset() != null && filter.getPageSize() != null) {
            int pageOffset = filter.getPageOffset() * filter.getPageSize();
            query.setFirstResult(Math.max(pageOffset, 0)); // Apply offset
            query.setMaxResults(filter.getPageSize());     // Apply limit
        }
    }

    /**
     * Adds Hibernate-specific query hints for caching and performance optimization.
     */
    public void addHibernateFilters(Query query) {
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
        query.setHint("org.hibernate.readOnly", true);
    }

    /**
     * Adds filtering logic for user group-based data access control.
     *
     * <p>Filters the data based on the user group of the current user.
     * If the user group is not found, an exception is thrown.
     */
    public void addUserGroupFilter() {
        Field field = getField("userGroup");
        if(field == null) return;

        String userGroup = SessionStorageUtil.getUserGroup();
        if(userGroup != null){
            List<String> userGroups = getUserGroups(userGroup);

            // Create predicates for each user group and combine with OR
            if (userGroups.size() > 1) {
                InString("userGroup", userGroups);
            } else {
                ILike("userGroup", userGroups.getFirst() + "%");
            }
        }else{
            throw new CommonException("User group is not found for the user");
        }

    }

    /**
     * Splits a user group string into hierarchical parts for filtering.
     *
     * @param userGroup The user group string.
     * @return A list of hierarchical user groups.
     */
    private static List<String> getUserGroups(String userGroup) {
        List<String> userGroups = new ArrayList<>();
        if(userGroup == null) return userGroups;
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

    /**
     * Gets a field from the superclass of the entity class.
     *
     * @param fieldName The name of the field.
     * @return The field object if found, otherwise null.
     */
    private Field getField(String fieldName) {
        try {
            return entityClass.getSuperclass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}