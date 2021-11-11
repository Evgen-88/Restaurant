package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.TestServiceConfiguration;
import com.itrex.kaliaha.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Test
    void findById() {

    }

    @Test
    void findAllTest_validData_shouldReturnAllExistingUsers() {
        //given
        int expectedSize = 3;

        // when
        Mockito.when(userRepository.findAll())
               .thenReturn(Arrays.asList(new User(), new User(), new User()));
        int actualSize = userService.findAll().size();

        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}