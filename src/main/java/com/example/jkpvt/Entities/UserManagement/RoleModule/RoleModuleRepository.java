package com.example.jkpvt.Entities.UserManagement.RoleModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleModuleRepository extends JpaRepository<RoleModule, Long> {
    Optional<RoleModule> findByRoleIdAndModuleId(Long roleId, Long moduleId);
  
    List<RoleModule> findByRoleId(Long roleId);
}
