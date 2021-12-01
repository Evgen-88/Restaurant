package com.itrex.kaliaha.config;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

@Configuration
public class ApplicationContextConfiguration {
    @Value("${logging.profile.info}")
    private String loggingProfileInfo;
    @Value("${logging.profile.debug}")
    private String loggingProfileDebug;

    @Bean
    @Profile("info")
    public void configureLoggingInfo() {
        PropertyConfigurator.configure(loggingProfileInfo);
    }

    @Bean
    @Profile("debug")
    public void configureLoggingDebug() {
        PropertyConfigurator.configure(loggingProfileDebug);
    }

    //remove the default ROLE_ prefix
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}