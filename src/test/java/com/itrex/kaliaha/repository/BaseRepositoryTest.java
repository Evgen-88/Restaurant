package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.service.FlywayService;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class BaseRepositoryTest {
    private final FlywayService flywayService;
    private final ApplicationContext applicationContext;

    public BaseRepositoryTest() {
        applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        flywayService = applicationContext.getBean(FlywayService.class);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Before
    public void initDB() {
        flywayService.migrate();
    }

    @After
    public void cleanDB() {
        flywayService.clean();
    }
}