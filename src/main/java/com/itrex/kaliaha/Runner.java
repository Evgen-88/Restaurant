package com.itrex.kaliaha;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;

import com.itrex.kaliaha.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        UserRepository userRepository = ctx.getBean(UserRepository.class);
        System.out.println(userRepository.findById(1L));
    }
}