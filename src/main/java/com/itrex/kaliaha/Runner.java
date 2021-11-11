package com.itrex.kaliaha;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.exception.ServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) throws ServiceException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
    }
}