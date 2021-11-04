package com.itrex.kaliaha;

import com.itrex.kaliaha.config.MigrationContextConfiguration;
import com.itrex.kaliaha.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MigrationContextConfiguration.class);
        ctx.getBean(UserRepository.class).findAll().forEach(System.out::println);
    }
}