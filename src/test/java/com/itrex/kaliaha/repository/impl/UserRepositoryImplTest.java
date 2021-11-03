package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.exception.AddMethodUserRepositoryImplException;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

@SpringJUnitConfig
@ContextConfiguration(classes = ApplicationContextConfiguration.class)
public class UserRepositoryImplTest extends BaseRepositoryTest {
    private final List<User> users;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DishRepository dishRepository;

    public UserRepositoryImplTest() {
        users = new ArrayList<>() {{
            add(new User(1L, "Коляго", "Владислав", "kaliaha.vladzislav", "1111", "г.Витебск"));
            add(new User(2L, "Молочко", "Юрий", "molochko.urey", "2222", "г.Хойники"));
            add(new User(3L, "Рубанов", "Владислав", "rubanov", "3333", "г.Жлобин"));
            add(new User(4L, "Петров", "Сергей", "petrov", "4444", "г.Москва"));
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnUserById() {
        //given
        User expected = users.get(0);

        //when
        User actual = userRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingUsers() {
        //given && when
        List<User> actual = userRepository.findAll();

        //then
        Assertions.assertEquals(users, actual);
    }

    @Test
    public void addTest_validData_shouldThrowException() {
        Assertions.assertThrowsExactly(AddMethodUserRepositoryImplException.class,
                () -> userRepository.add(new User())
        );
    }

    @Test
    public void addTest_validData_shouldAddNewUserWithRoles() {
        //given
        List<User> expected = userRepository.findAll();
        List<Role> roles = new ArrayList<>() {{
            add(new Role(1L));
            add(new Role(2L));
        }};

        Assertions.assertEquals(users.size(), expected.size());

        //when
        User newActual = new User("Сидоров", "Александр", "suitor", "5555", "г.Минск");
        boolean isAdded = userRepository.add(newActual, roles);
        User newExpected = new User(5L, "Сидоров", "Александр", "suitor", "5555", "г.Минск");
        expected.add(newExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newExpected, newActual);
        Assertions.assertEquals(newExpected, userRepository.findById(newActual.getId()));
        Assertions.assertEquals(roles.size(), userRepository.findRolesByUserId(newActual.getId()).size());
    }

    @Test
    public void updateTest_validData_shouldUpdateUser() {
        //given
        User expected = new User(1L, "Updated Коляго", "Updated Владислав", "Updated kaliaha", "Updated 2222", "Updated г.Минск");
        User actual = userRepository.findById(expected.getId());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setLastName("Updated Коляго");
        actual.setFirstName("Updated Владислав");
        actual.setLogin("Updated kaliaha");
        actual.setPassword("Updated 2222");
        actual.setAddress("Updated г.Минск");
        boolean isUpdated = userRepository.update(actual);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, userRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteUser() {
        //given
        User expected = users.get(0);
        User actual = userRepository.findById(1L);
        List<Order> actualOrders = orderRepository.findOrdersByUserId(actual.getId());
        List<Dish> orderedDishes = dishRepository.findAllDishesInOrderById(actualOrders.get(0).getId());

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(2, userRepository.findRolesByUserId(actual.getId()).size());
        Assertions.assertEquals(3, actualOrders.size());

        //when
        boolean isDeleted = userRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(userRepository.findById(1L));
        Assertions.assertEquals(0, userRepository.findRolesByUserId(actual.getId()).size());
        Assertions.assertEquals(0, orderRepository.findOrdersByUserId(actual.getId()).size());
        Assertions.assertNotNull(dishRepository.findById(orderedDishes.get(0).getId()));
        Assertions.assertNotNull(dishRepository.findById(orderedDishes.get(1).getId()));
    }

    @Test
    public void findRolesByUserIdTest_validData_shouldReturnUserRoles() {
        //given
        User user = userRepository.findById(1L);

        // when
        List<Role> actual = userRepository.findRolesByUserId(user.getId());

        //then
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    public void deleteRoleFromUserByIdTest_validData_shouldDeleteUserRole() {
        //given
        User user = userRepository.findById(1L);
        List<Role> userRolesExpected = userRepository.findRolesByUserId(user.getId());

        Assertions.assertEquals(2, userRolesExpected.size());

        //when
        Role roleToDelete = userRolesExpected.remove(1);
        boolean isDeleted = userRepository.deleteRoleFromUserById(user.getId(), roleToDelete.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(userRolesExpected, userRepository.findRolesByUserId(user.getId()));
    }
}