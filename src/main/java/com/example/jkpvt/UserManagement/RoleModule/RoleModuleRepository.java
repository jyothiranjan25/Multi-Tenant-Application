package com.example.jkpvt.UserManagement.RoleModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleModuleRepository extends JpaRepository<RoleModule, Long> {
}
