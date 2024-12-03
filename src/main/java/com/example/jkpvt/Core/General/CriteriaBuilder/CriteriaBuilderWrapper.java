package com.example.jkpvt.Core.General.CriteriaBuilder;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.General.CommonFilterDTO;
import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Predicate orPredicate;

    private final Map<String, Join<T, ?>> joins = new HashMap<>();

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
        this.orPredicate = criteriaBuilder.disjunction();
    }

    /**
     * Constructs and returns the final query with applied filters, pagination, and cache hints.
     *
     * @return A Query object ready for execution.
     */
    public Query buildFinalQuery() {
        addUserGroupFilter();
        applyDefaultOrderById();
        criteriaQuery.where(buildPredicate());
        Query query = session.createQuery(criteriaQuery);
        applyPaginationFilters(query);
        addHibernateFilters(query);
        return query;
    }

    private Predicate buildPredicate() {
        if (finalPredicate.getExpressions().size() >1 && orPredicate.getExpressions().size() > 1) {
            return criteriaBuilder.and(finalPredicate, orPredicate);
        } else if(finalPredicate.getExpressions().size() < 2 && orPredicate.getExpressions().size() > 1) {
            return orPredicate;
        } else {
            return finalPredicate;
        }
    }

    /**
     * Executes the query and returns the result list.
     *
     * @return A list of entities matching the query.
     */
    public List<T> getResultList() {
        List<T> result = buildFinalQuery().getResultList();
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
        Equal(key, value, true);
    }

    /**
     * Adds an equal condition to the query with the given operator.
     *
     * @param key   The field name.
     * @param value The value to match.
     * @param isAnd True for AND operator, false for OR operator.
     */
    public void Equal(String key, Object value, boolean isAnd) {
        addPredicate(criteriaBuilder.equal(getExpression(key), value), isAnd);
    }

    /**
     * Adds a "not equal" condition to the query.
     *
     * @param key   The field name.
     * @param value The value to match.
     */
    public void NotEqual(String key, Object value) {
        NotEqual(key, value, true);
    }

    /**
     * Adds a "not equal" condition to the query.
     *
     * @param key   The field name.
     * @param value The value to match.
     * @param isAnd True for AND operator, false for OR operator.
     */
    public void NotEqual(String key, Object value, boolean isAnd) {
        addPredicate(criteriaBuilder.notEqual(getExpression(key), value), isAnd);
    }

    /**
     * Adds a "greater than" condition to the query.
     *
     * @param key   The field name.
     * @param value The value to match.
     */
    public void Like(String key, String value) {
        Like(key, value, true);
    }

    /**
     * Adds a "like" condition to the query for partial matching.
     *
     * @param key   The field name.
     * @param value The value to match.
     * @param isAnd True for AND operator, false for OR operator.
     */
    public void Like(String key, String value, boolean isAnd) {
        addPredicate(criteriaBuilder.like(getExpression(key).as(String.class), value), isAnd);
    }

    /**
     * Adds a case-insensitive "like" condition to the query.
     *
     * @param key   The field name.
     * @param value The value to match.
     */
    public void ILike(String key, String value) {
        ILike(key, value, true);
    }

    /**
     * Adds a case-insensitive "like" condition to the query.
     *
     * @param key   The field name.
     * @param value The value to match.
     * @param isAnd True for AND operator, false for OR operator.
     */
    public void ILike(String key, String value, boolean isAnd) {
        addPredicate(criteriaBuilder.ilike(criteriaBuilder.toString(getExpression(key).as(Character.class)), value), isAnd);
    }

    /**
     * Adds an "IN" condition for a list of strings.
     *
     * @param key    The field name.
     * @param values The list of values to match.
     */
    public <U> void In(String key, List<U> values) {
        In(key, values, true);
    }

    /**
     * Adds an "IN" condition for a list of strings.
     *
     * @param key    The field name.
     * @param values The list of values to match.
     * @param isAnd True for AND operator, false for OR operator.
     */
    public <U> void In(String key, List<U> values, boolean isAnd) {
        addPredicate(getExpression(key).in(values), isAnd);
    }

    /**
     * Adds a join condition to the query.
     *
     * @param key The field name to join.
     */
    public void join(String key) {
        join(key, key);
    }

    /**
     * Adds a join condition to the query.
     *
     * @param key The field name to join.
     * @param alias The alias for the joined entity.
     */
    public void join(String key, String alias) {
        join(key, alias, JoinType.LEFT);
    }

    /**
     * Adds a join condition to the query.
     *
     * @param key The field name to join.
     * @param alias The alias for the joined entity.
     * @param joinType The type of join to perform.
     */
    public void join(String key, String alias, JoinType joinType) {
        Join<T, ?> join = root.join(key, joinType);
        join.alias(alias);
        joins.put(alias, join);
    }

    private Expression<T> getExpression(String key) {
        String[] parts = key.split("\\.");
        if (parts.length == 2) {
            String alias = parts[0];
            String field = parts[1];
            Join<T, ?> join = joins.get(alias);
            if (join != null) {
                return join.get(field);
            }else {
                throw new CommonException("Join not found for alias: " + alias);
            }
        }
        return root.get(key);
    }


    /**
     * Adds ORDER BY clause to the query.
     *
     * @param key       The field name to order by.
     * @param ascending True for ascending order, false for descending order.
     *
     * Default order is by ID in ascending order.
     */
    public void OrderBy(String key, boolean ascending) {
        List<Order> currentOrders = new ArrayList<>(criteriaQuery.getOrderList());

        // Remove default 'ORDER BY id ASC' if it exists
        if (!currentOrders.isEmpty() && currentOrders.getFirst().getExpression().equals(root.get("id"))) {
            currentOrders.clear();
        }

        // Add new ORDER BY clause
        if (ascending) {
            currentOrders.add(criteriaBuilder.asc(getExpression(key)));
        } else {
            currentOrders.add(criteriaBuilder.desc(getExpression(key)));
        }

        // Set the updated order list back to the query
        criteriaQuery.orderBy(currentOrders);
    }

    /**
     * default Orders the query results by ID in ascending order.
     */
    private void applyDefaultOrderById() {
        if (criteriaQuery.getOrderList().isEmpty()) {
            OrderBy("id", true);
        }
    }

    /**
     * Applies pagination filters (offset and limit) to the query.
     *
     * @param query  The query object to modify.
     */
    private void applyPaginationFilters(Query query) {
        if (filter.getPageOffset() != null && filter.getPageSize() != null) {
            int pageOffset = filter.getPageOffset() * filter.getPageSize();
            query.setFirstResult(Math.max(pageOffset, 0)); // Apply offset
            query.setMaxResults(filter.getPageSize());     // Apply limit
        }
    }

    /**
     * Adds Hibernate-specific query hints for caching and performance optimization.
     */
    private void addHibernateFilters(Query query) {
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
    private void addUserGroupFilter() {
        Field field = getField("userGroup");
        if(field == null) return;

        String userGroup = SessionStorageUtil.getUserGroup();
        if(userGroup != null){
            List<String> userGroups = getUserGroups(userGroup);

            // Create predicates for each user group and combine with OR
            if (userGroups.size() > 1) {
                In("userGroup", userGroups);
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


    /**
     * Adds a predicate to the final predicate with the given operator.
     *
     * @param predicate The predicate to add.
     * @param isAnd     True for AND operator, false for OR operator.
     */
    private void addPredicate(Predicate predicate, boolean isAnd) {
        if (isAnd) {
            addAndPredicate(predicate);
        } else {
            addOrPredicate(predicate);
        }
    }

    /**
     * Combines the given predicate with the final predicate using the AND operator.
     *
     * @param predicate The predicate to add.
     */
    private void addAndPredicate(Predicate predicate) {
        finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
    }

    /**
     * Combines the given predicate with the final predicate using the OR operator.
     *
     * @param predicate The predicate to add.
     */
    private void addOrPredicate(Predicate predicate) {
        orPredicate = criteriaBuilder.or(orPredicate, predicate);
    }
}