package com.example.jkpvt.Entities.SearchFilter;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchFilterService {
    @PersistenceContext
    private EntityManager entityManager;

    public <T, U> List<T> search(Class<T> entityClass,String searchTerm, U dto) {
        try {
            // Get the criteria builder
            HibernateCriteriaBuilder cb = (HibernateCriteriaBuilder) entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(entityClass);
            Root<T> root = query.from(entityClass);

            // Create a list of predicates
            List<Predicate> predicates = new ArrayList<>();

            // Get all fields from the entity and DTO
            Field[] entityFields = entityClass.getDeclaredFields();
            Field[] dtoFields = dto.getClass().getDeclaredFields();

            // If search term is provided, build predicates for each string-like field
            if (searchTerm != null && !searchTerm.isEmpty()) {
                // Build predicates for each string-like field
                Predicate orPredicate = cb.disjunction();
                for (var field : entityFields) {
                    if (field.getType().equals(String.class)) {
                        orPredicate = cb.or(orPredicate, cb.ilike(root.get(field.getName()), "%" + searchTerm + "%"));
                    } else if (Number.class.isAssignableFrom(field.getType())) {
                        orPredicate = cb.or(orPredicate, cb.ilike(cb.toString(root.get(field.getName())),"%" + searchTerm + "%"));
                    }else if (field.getType().equals(Boolean.class)) {
                        orPredicate = cb.or(orPredicate, cb.ilike(cb.toString(root.get(field.getName())),"%" + searchTerm + "%"));
                    } else if (field.getType().isEnum()) {
                        orPredicate = cb.or(orPredicate, cb.ilike(cb.toString(root.get(field.getName())),"%" + searchTerm + "%"));
                    }
                }
                predicates.add(orPredicate);
            }

            // Build predicates for each field in the DTO
            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                for (Field entityField : entityFields) {
                    if (entityField.getName().equals(dtoField.getName()) && entityField.getType().equals(dtoField.getType())) {
                        Object value = dtoField.get(dto);
                        if (value != null) {
                            predicates.add(cb.ilike(root.get(entityField.getName()), value.toString()));
                        }
                    }
                }
            }

            // Add all predicates to the query
            query.where(predicates.toArray(new Predicate[0]));
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
