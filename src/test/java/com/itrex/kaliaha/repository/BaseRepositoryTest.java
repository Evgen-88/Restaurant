package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.config.TestApplicationContextConfiguration;
import org.flywaydb.core.Flyway;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(TestApplicationContextConfiguration.class)
public abstract class BaseRepositoryTest {
    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void migrate() {
        flyway.migrate();
    }

    @AfterEach
    public void clean() {
        flyway.clean();
    }
}