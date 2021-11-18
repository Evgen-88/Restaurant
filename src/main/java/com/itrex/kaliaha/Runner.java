package com.itrex.kaliaha;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;

import com.itrex.kaliaha.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

@Slf4j
public class Runner {
    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "info");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        System.out.print(userRepository.findById(1L));

        log.info("info");
        log.debug("debug");
    }
}