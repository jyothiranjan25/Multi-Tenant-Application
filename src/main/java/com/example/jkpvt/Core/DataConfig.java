package com.example.jkpvt.Core;

import com.example.jkpvt.Core.Conditions.DataSourceInitializerCondition;
import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Conditional(DataSourceInitializerCondition.class)
public class DataConfig {

    private final DataSource dataSource;
    final static String PATH_URL = "/Database/InitialData.sql";

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        if (new ClassPathResource(PATH_URL).exists()) {
            DataSourceInitializer initializer = new DataSourceInitializer();
            initializer.setDataSource(dataSource);  // Use the DataSource from XML

            // Create a ResourceDatabasePopulator to load the data.sql file
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            databasePopulator.addScript(new ClassPathResource(PATH_URL));  // Add your SQL script

            // Set the database populator to execute the script
            initializer.setDatabasePopulator(databasePopulator);
            initializer.setEnabled(true);  // Ensure it's enabled

            return initializer;
        }else{
           throw new CommonException("Initial data file not found");
        }
    }
}