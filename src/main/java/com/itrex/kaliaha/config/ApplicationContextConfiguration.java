package com.itrex.kaliaha.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
@PropertySource("classpath:/application.properties")
public class ApplicationContextConfiguration {
    @Bean
    @Profile("info")
    public void configureLoggingInfo() {
        PropertyConfigurator.configure("src/main/resources/log4j_info.properties");
    }

    @Bean
    @Profile("debug")
    public void configureLoggingDebug() {
        PropertyConfigurator.configure("src/main/resources/log4j_debug.properties");
    }
}