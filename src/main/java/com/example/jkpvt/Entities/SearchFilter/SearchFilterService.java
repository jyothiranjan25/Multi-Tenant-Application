package com.example.jkpvt.Entities.SearchFilter;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchFilterService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public <T, U> List<T> search(Class<T> entityClass, String searchTerm, U dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
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
                predicates.add(buildSearchTermPredicate(cb, root, searchTerm, entityFields));
            }

            // Build predicates for each field in the DTO
            predicates.addAll(buildDtoPredicate(cb, root, entityFields, dtoFields, dto));

            // Add all predicates to the query
            query.where(predicates.toArray(new Predicate[0]));
            query.orderBy(cb.asc(root.get("id")));
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private <T> Predicate addStringTermPredicates(HibernateCriteriaBuilder cb, Root<T> root, String searchTerm, Field[] entityFields) {
        // Build predicates for each string-like field
        Predicate orPredicate = cb.disjunction();
        for (var field : entityFields) {
            if (field.getType().equals(String.class)) {
                orPredicate = cb.or(orPredicate, cb.ilike(root.get(field.getName()), "%" + searchTerm + "%"));
            } else if (Number.class.isAssignableFrom(field.getType()) || field.getType().equals(Boolean.class) || field.getType().isEnum()) {
                orPredicate = cb.or(orPredicate, cb.ilike(cb.toString(root.get(field.getName())), "%" + searchTerm + "%"));
            }
        }
        return orPredicate;
    }

    private <T> Predicate buildSearchTermPredicate(HibernateCriteriaBuilder cb, Root<T> root, String searchTerm, Field[] entityFields) {
        List<Predicate> predicates = new ArrayList<>();
        Arrays.stream(entityFields)
                .filter(field -> field.getType().equals(String.class) || Number.class.isAssignableFrom(field.getType())
                        || field.getType().equals(Boolean.class) || field.getType().isEnum())
                .forEach(field -> {
                    if (field.getType().equals(String.class)) {
                        predicates.add(cb.ilike(root.get(field.getName()), "%" + searchTerm + "%"));
                    } else {
                        predicates.add(cb.ilike(cb.toString(root.get(field.getName())), "%" + searchTerm + "%"));
                    }
                });
        return cb.or(predicates.toArray(new Predicate[0]));
    }

    private <T,U> List<Predicate> addDtoPredicates(HibernateCriteriaBuilder cb, Root<T> root, Field[] entityFields,Field[] dtoFields,U dto) {
        try {
            List<Predicate> predicates = new ArrayList<>();
            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                for (Field entityField : entityFields) {
                    if (entityField.getName().equals(dtoField.getName()) && entityField.getType().equals(dtoField.getType())) {
                        Object value = dtoField.get(dto);
                        if (value != null && !value.toString().isEmpty()) {
                            if(entityField.getType().equals(String.class))
                                predicates.add(cb.ilike(root.get(entityField.getName()), value.toString()));
                            else
                                predicates.add(cb.equal(root.get(entityField.getName()), value));
                        }
                    }
                }
            }
            return predicates;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private <T,U> List<Predicate> buildDtoPredicate(HibernateCriteriaBuilder cb, Root<T> root, Field[] entityFields,Field[] dtoFields,U dto) {
        try {
            List<Predicate> predicates = new ArrayList<>();
            Arrays.stream(dtoFields).forEach(
                    dtoField ->{
                        dtoField.setAccessible(true);
                        Arrays.stream(entityFields)
                                .filter(entityField -> entityField.getName().equals(dtoField.getName())
                                        && entityField.getType().equals(dtoField.getType()))
                                .forEach(entityField ->{
                                    try {
                                        Object value = dtoField.get(dto);
                                        if (value != null && !value.toString().isEmpty()) {
                                            if (entityField.getType().equals(String.class))
                                                predicates.add(cb.ilike(root.get(entityField.getName()), value.toString()));
                                            else
                                                predicates.add(cb.equal(root.get(entityField.getName()), value));
                                        }
                                    } catch (Exception e) {
                                        throw new CommonException(e.getMessage());
                                    }
                                });
                    }
            );
            return predicates;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
