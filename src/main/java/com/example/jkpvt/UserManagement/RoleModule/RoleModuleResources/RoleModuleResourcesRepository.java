package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Roles.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleModuleResourcesRepository extends JpaRepository<RoleModuleResources, Long> {
   List<RoleModuleResources> findByRoleInAndModuleInAndResourceIn(Collection<Roles> role, Collection<Modules> module, Collection<Resources> resource);
}
