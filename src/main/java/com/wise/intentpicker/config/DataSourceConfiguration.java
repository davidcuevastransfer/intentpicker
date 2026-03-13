package com.wise.intentpicker.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {

  @Bean
  @Primary
  public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
    HikariDataSource hikariDataSource = dataSourceProperties
        .initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
    hikariDataSource.setPoolName("intentpicker-postgres");
    hikariDataSource.setMaximumPoolSize(30);
    hikariDataSource.setMinimumIdle(30);
    hikariDataSource.setConnectionTimeout(500);
    hikariDataSource.setValidationTimeout(250);
    return hikariDataSource;
  }

  @Bean
  @FlywayDataSource
  public HikariDataSource flywayDataSource(FlywayProperties properties) {
    final HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setJdbcUrl(properties.getUrl());
    hikariDataSource.setUsername(properties.getUser());
    hikariDataSource.setPassword(properties.getPassword());
    hikariDataSource.setPoolName("intentpicker-flyway");
    hikariDataSource.setMaximumPoolSize(2);
    hikariDataSource.setMinimumIdle(0);
    return hikariDataSource;
  }
}
