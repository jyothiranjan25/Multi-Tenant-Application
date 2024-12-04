package com.example.jkpvt.Core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class AuditTablesCleanupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllAuditTables() {
        return jdbcTemplate.queryForList("SELECT table_name FROM information_schema.tables WHERE table_name LIKE '%_aud'", String.class);
    }

    // run every second
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupAuditData() {
        try {
            List<String> auditTables = getAllAuditTables();

            // Calculate the threshold date (3 months ago)
            LocalDateTime thresholdDate = LocalDateTime.now().minusMonths(3);
            long thresholdEpochMillis = thresholdDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            // Delete from audit tables
            for (String table : auditTables) {
                String deleteAuditSql = String.format(
                        "DELETE FROM %s WHERE rev IN (SELECT rev FROM revinfo WHERE revtstmp < ?)",
                        table
                );
                System.out.println("Executing SQL: " + deleteAuditSql + " with thresholdEpochMillis: " + thresholdEpochMillis);
                jdbcTemplate.update(deleteAuditSql, thresholdEpochMillis);
            }

            if(!auditTables.isEmpty()) {
            // Delete from REVINFO table
            String deleteRevInfoSql = "DELETE FROM REVINFO WHERE revtstmp < ?";
            System.out.println("Executing SQL: " + deleteRevInfoSql + " with thresholdEpochMillis: " + thresholdEpochMillis);
            jdbcTemplate.update(deleteRevInfoSql, thresholdEpochMillis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
