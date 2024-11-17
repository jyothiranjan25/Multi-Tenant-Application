package com.example.jkpvt.UserManagement.Resources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long> {
    List<Resources> findByIdIn(List<Long> ids);
}
