package com.itrex.kaliaha.config;

import org.flywaydb.core.Flyway;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.itrex.kaliaha")
@PropertySource("classpath:/database.properties")
public class ApplicationContextConfiguration {
    @Value("${database.url}")
    private String url;
    @Value("${database.user}")
    private String user;
    @Value("${database.password}")
    private String password;
    @Value("${database.migration.location}")
    private String migrationLocation;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(url, user, password)
                .locations(migrationLocation)
                .load();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }
}