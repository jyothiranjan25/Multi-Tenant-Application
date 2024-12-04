package com.example.jkpvt.Entities.UserManagement.RoleModule;

import com.example.jkpvt.Entities.UserManagement.Modules.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleModuleRepository extends JpaRepository<RoleModule, Long> {
   Optional<RoleModule> findByRoleIdAndModuleId(Long roleId, Long moduleId);
   List<RoleModule> findByRoleIdAndModuleIn(Long role_id, Collection<Modules> module);
}
