package com.example.jkpvt.Entities.UserManagement.UserLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginDetailsRepository extends JpaRepository<UserLoginDetails, Long> {
    Optional<Object> findBySessionId(String sessionId);
}
