package com.example.jkpvt.UserManagement.RoleModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleModuleRepository extends JpaRepository<RoleModule, Long> {
   Optional<RoleModule> findByRoleIdAndModuleId(Long roleId, Long moduleId);
}
