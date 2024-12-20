package com.example.jkpvt.Core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class InitialDataConfig {

    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;

    @Value("${initialize.initialData.enabled}")
    private boolean isEnabled;

    @Value("${initialize.initialData.path}")
    private String path;

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        if (isEnabled && resourceLoader.getResource(path).exists()) {
            DataSourceInitializer initializer = new DataSourceInitializer();
            initializer.setDataSource(dataSource);  // Use the DataSource from XML

            // Create a ResourceDatabasePopulator to load the data.sql file
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            databasePopulator.addScript(resourceLoader.getResource(path));  // Add your SQL script

            // Set the database populator to execute the script
            initializer.setDatabasePopulator(databasePopulator);
            initializer.setEnabled(true);  // Ensure it's enabled

            return initializer;
        }
        return null;
    }
}