package com.itrex.kaliaha;

import com.itrex.kaliaha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@SpringBootApplication
public class Runner implements CommandLineRunner {
    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        userRepository.findById(1L);
    }
}