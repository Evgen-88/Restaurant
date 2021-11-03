package com.itrex.kaliaha.config;

import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.itrex.kaliaha.property.Properties.*;
import static com.itrex.kaliaha.property.Properties.MIGRATIONS_LOCATION;

@Configuration
@ComponentScan("com.itrex.kaliaha")
public class ApplicationContextConfiguration {

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(H2_URL, H2_USER, H2_PASSWORD)
                .locations(MIGRATIONS_LOCATION)
                .load();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Bean
    public Session session() {
        return sessionFactory().openSession();
    }
}