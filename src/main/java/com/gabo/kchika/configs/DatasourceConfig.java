package com.gabo.kchika.configs;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Driver;

public class DatasourceConfig {
    
    public DataSource dataSource() {
        final var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");;
        dataSource.setUsername("sa");
        dataSource.setPassword("password");

        return dataSource;
    }
}
