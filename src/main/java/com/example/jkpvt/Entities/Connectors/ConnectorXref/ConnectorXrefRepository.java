package com.example.jkpvt.Entities.Connectors.ConnectorXref;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectorXrefRepository extends JpaRepository<ConnectorXref, Long> {
}
