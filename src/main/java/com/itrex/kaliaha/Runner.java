package com.itrex.kaliaha;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.service.DishService;
import com.itrex.kaliaha.service.impl.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        DishService dishService = ctx.getBean(DishService.class);
        dishService.findById(1L).getIngredientList().forEach(System.out::println);
    }
}