package com.itrex.kaliaha.config;

import com.itrex.kaliaha.repository.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.itrex.kaliaha.service")
public class TestServiceConfiguration {
    @Bean
    public CompositionRepository compositionRepository() {
        return Mockito.mock(CompositionRepository.class);
    }

    @Bean
    public DishRepository dishRepository() {
        return Mockito.mock(DishRepository.class);
    }

    @Bean
    public IngredientRepository ingredientRepository() {
        return Mockito.mock(IngredientRepository.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public RoleRepository roleRepository() {
        return Mockito.mock(RoleRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }
}