package com.itrex.kaliaha.repository;

import org.flywaydb.core.Flyway;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRepositoryTest {
    @Autowired
    private Flyway flyway;

    @AfterEach
    public void cleanDB() {
        flyway.clean();
        flyway.migrate();
    }
}