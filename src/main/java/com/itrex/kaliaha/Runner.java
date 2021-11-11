package com.itrex.kaliaha;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.repository.DishRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        DishRepository dishRepository = ctx.getBean(DishRepository.class);
        System.out.println(dishRepository.getCompositionsByDishId(1L));
    }
}