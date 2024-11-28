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

    public CriteriaBuilderWrapper(Class<T> entityClass, Session session, CommonFilterDTO filter) {
        this.entityClass = entityClass;
        this.session = session;
        this.filter = filter;
        this.criteriaBuilder = session.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        this.root = criteriaQuery.from(entityClass);
        this.finalPredicate = criteriaBuilder.conjunction();
    }

    private Query getFinalQuery() {
        addUserGroupFilter();
        criteriaQuery.where(finalPredicate);
        Query query = session.createQuery(criteriaQuery);
        addPaginationFilters(filter, query);
        addHibernateFilters(query);
        return query;
    }

    public List<T> getResultList() {
        List<T> result = getFinalQuery().getResultList();
        filter.setTotalCount(result.size());
        return result;
    }

    /**
     *  Equal Predicate
     * @param key
     * @param value
     */
    public void Equal(String key, Object value) {
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get(key), value));
    }

    public void NotEqual(String key, Object value) {
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.notEqual(root.get(key), value));
    }

    public void Like(String key, String value) {
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.like(root.get(key), value));
    }

    public void ILike(String key, String value) {
        finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.ilike(root.get(key), value));
    }

    public void InObjects(String key, List<Object> values) {
        finalPredicate = criteriaBuilder.and(finalPredicate, root.get(key).in(values));
    }

    public void InString(String key, List<String> values) {
        finalPredicate = criteriaBuilder.and(finalPredicate, root.get(key).in(values));
    }


    public void addPaginationFilters(CommonFilterDTO filter, Query query) {
        // Apply pagination if both fields are provided
        if (filter.getPageOffset() != null && filter.getPageSize() != null) {
            int pageOffset = filter.getPageOffset();
            int pageSize = filter.getPageSize();
            // Adjust the pageOffset to be 0-based (since JPA uses 0-based index)
            if (pageOffset < 0) pageOffset = 0;
            else pageOffset = (pageOffset) * pageSize;

            // Apply pagination
            query.setFirstResult(pageOffset);  // Set the offset
            query.setMaxResults(pageSize);     // Set the limit
        }
    }

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

    private Field getField(String fieldName) {
        try {
            return entityClass.getSuperclass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}