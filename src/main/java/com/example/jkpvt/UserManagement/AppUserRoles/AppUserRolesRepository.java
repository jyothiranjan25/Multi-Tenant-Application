package com.example.jkpvt.UserManagement.AppUserRoles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRolesRepository extends JpaRepository<AppUserRoles, Long> {
}
