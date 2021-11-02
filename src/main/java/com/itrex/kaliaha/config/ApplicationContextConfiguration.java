package com.itrex.kaliaha.config;

import com.itrex.kaliaha.service.FlywayService;
import com.itrex.kaliaha.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.itrex.kaliaha")
public class ApplicationContextConfiguration {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FlywayService flywayService() {
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();
        return flywayService;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.getSessionFactory();
    }
}