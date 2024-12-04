package com.example.jkpvt.Entities.SearchFilter;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.General.CommonFilterDTO;
import com.example.jkpvt.Core.General.CriteriaBuilder.CriteriaBuilderWrapper;
import com.example.jkpvt.Core.Messages.CommonMessages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchFilterService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public <T, U> List<T> search(Class<T> entityClass, String searchTerm, U dto) {
        try(Session session = entityManager.unwrap(Session.class)) {
            CommonFilterDTO commonFilterDTO = (CommonFilterDTO) dto;
            // Get the criteria builder
            CriteriaBuilderWrapper<T> cbw = new CriteriaBuilderWrapper<>(entityClass, session, commonFilterDTO);

            // Get all fields from the entity and DTO
            Field[] entityFields = entityClass.getDeclaredFields();
            Field[] dtoFieldswithoutid = dto.getClass().getDeclaredFields();

            // add id field to dtoFields
            Field[] dtoFields = Arrays.copyOf(dtoFieldswithoutid, dtoFieldswithoutid.length + 1);
            dtoFields[dtoFieldswithoutid.length] = dto.getClass().getSuperclass().getDeclaredField("id");

            // If search term is provided, build predicates for each string-like field
            if (searchTerm != null && !searchTerm.isEmpty()) {
                buildSearchTermPredicate(cbw, searchTerm, entityFields);
            }

            // Build predicates for each field in the DTO
            buildDtoPredicate(cbw, entityFields, dtoFields, dto);

            // Get the result list
            return cbw.getResultList();
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private void addStringTermPredicates(CriteriaBuilderWrapper cbw, String searchTerm, Field[] entityFields) {
        for (var field : entityFields) {
            if (field.getType().equals(String.class) || Number.class.isAssignableFrom(field.getType()) || field.getType().equals(Boolean.class) || field.getType().isEnum()) {
                cbw.ILike(field.getName(), "%" + searchTerm + "%",false);
            }
        }
    }

    private void buildSearchTermPredicate(CriteriaBuilderWrapper cbw, String searchTerm, Field[] entityFields) {
        Arrays.stream(entityFields)
                .filter(field -> field.getType().equals(String.class) || Number.class.isAssignableFrom(field.getType())
                        || field.getType().equals(Boolean.class) || field.getType().isEnum())
                .forEach(field -> {
                    cbw.ILike(field.getName(), "%" + searchTerm + "%",false);
                });
    }

    private <U> void addDtoPredicates(CriteriaBuilderWrapper cbw, Field[] entityFields,Field[] dtoFields,U dto) {
        try {
            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                for (Field entityField : entityFields) {
                    if (entityField.getName().equals(dtoField.getName()) && entityField.getType().equals(dtoField.getType())) {
                        Object value = dtoField.get(dto);
                        if (value != null && !value.toString().isEmpty()) {
                            if(entityField.getType().equals(String.class))
                                cbw.ILike(entityField.getName(), value.toString());
                            else
                                cbw.Equal(entityField.getName(), value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private <U> void buildDtoPredicate(CriteriaBuilderWrapper cbw, Field[] entityFields,Field[] dtoFields,U dto) {
        try {
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
                                                cbw.ILike(entityField.getName(), value.toString());
                                            else
                                                cbw.Equal(entityField.getName(), value);
                                        }
                                    } catch (Exception e) {
                                        throw new CommonException(CommonMessages.APPLICATION_ERROR);
                                    }
                                });
                    }
            );
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }
}
