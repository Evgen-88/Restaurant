package com.itrex.kaliaha;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.dto.DishUpdateDTO;
import com.itrex.kaliaha.enums.DishGroup;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.DishService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) throws ServiceException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        DishService dishService = ctx.getBean(DishService.class);
//        DishUpdateDTO dishUpdateDTO =  DishUpdateDTO.builder()
//                .id(1L).dishGroup(DishGroup.SALAD).dishName("фирменное блюдо")
//                .dishDescription("delicious").price(5400).imagePath("asd1")
//                .build();
        DishUpdateDTO dishUpdateDTO =  DishUpdateDTO.builder()
                .id(1L).dishGroup(DishGroup.HOT).dishName("update")
                .dishDescription("der").price(10).imagePath("s")
                .build();
        dishService.update(dishUpdateDTO);
    }
}