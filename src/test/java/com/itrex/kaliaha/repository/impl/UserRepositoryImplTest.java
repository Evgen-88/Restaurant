package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.exception.AddMethodUserRepositoryImplException;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImplTest extends BaseRepositoryTest {
    private final UserRepository userRepository;
    private final List<User> users;

    public UserRepositoryImplTest() {
        userRepository = getApplicationContext().getBean(UserRepositoryImpl.class);
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
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingUsers() {
        //given && when
        List<User> actual = userRepository.findAll();

        //then
        Assert.assertEquals(users, actual);
    }

    @Test(expected = AddMethodUserRepositoryImplException.class)
    public void addTest_validData_shouldThrowException() {
        userRepository.add(new User());
    }

    @Test
    public void addTest_validData_shouldAddNewUserWithRoles() {
        //given
        List<User> expected = userRepository.findAll();
        List<Role> roles = new ArrayList<>() {{
            add(new Role(1L));
            add(new Role(2L));
        }};

        Assert.assertEquals(users.size(), expected.size());

        //when
        User newActual = new User("Сидоров", "Александр", "suitor", "5555", "г.Минск");
        boolean isAdded = userRepository.add(newActual, roles);
        User newExpected = new User(5L, "Сидоров", "Александр", "suitor", "5555", "г.Минск");
        expected.add(newExpected);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(newExpected, newActual);
        Assert.assertEquals(newExpected, userRepository.findById(newActual.getId()));
        Assert.assertEquals(roles.size(), userRepository.findRolesByUserId(newActual.getId()).size());
    }

    @Test
    public void updateTest_validData_shouldUpdateUser() {
        //given
        User expected = new User(1L, "Updated Коляго", "Updated Владислав", "Updated kaliaha", "Updated 2222", "Updated г.Минск");
        User actual = userRepository.findById(expected.getId());

        Assert.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setLastName("Updated Коляго");
        actual.setFirstName("Updated Владислав");
        actual.setLogin("Updated kaliaha");
        actual.setPassword("Updated 2222");
        actual.setAddress("Updated г.Минск");
        boolean isUpdated = userRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, userRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteUser() {
        OrderRepository orderRepository = getApplicationContext().getBean(OrderRepositoryImpl.class);
        DishRepository dishRepository = getApplicationContext().getBean(DishRepositoryImpl.class);

        //given
        User expected = users.get(0);
        User actual = userRepository.findById(1L);
        List<Order> actualOrders = orderRepository.findOrdersByUserId(actual.getId());
        List<Dish> orderedDishes = dishRepository.findAllDishesInOrderById(actualOrders.get(0).getId());

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(2, userRepository.findRolesByUserId(actual.getId()).size());
        Assert.assertEquals(3, actualOrders.size());

        //when
        boolean isDeleted = userRepository.delete(actual.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(userRepository.findById(1L));
        Assert.assertEquals(0, userRepository.findRolesByUserId(actual.getId()).size());
        Assert.assertEquals(0, orderRepository.findOrdersByUserId(actual.getId()).size());
        Assert.assertNotNull(dishRepository.findById(orderedDishes.get(0).getId()));
        Assert.assertNotNull(dishRepository.findById(orderedDishes.get(1).getId()));
    }

    @Test
    public void findRolesByUserIdTest_validData_shouldReturnUserRoles() {
        //given
        User user = userRepository.findById(1L);

        // when
        List<Role> actual = userRepository.findRolesByUserId(user.getId());

        //then
        Assert.assertEquals(2, actual.size());
    }

    @Test
    public void deleteRoleFromUserByIdTest_validData_shouldDeleteUserRole() {
        UserRepository userRepository = getApplicationContext().getBean(UserRepositoryImpl.class);

        //given
        User user = userRepository.findById(1L);
        List<Role> userRolesExpected = userRepository.findRolesByUserId(user.getId());

        Assert.assertEquals(2, userRolesExpected.size());

        //when
        Role roleToDelete = userRolesExpected.remove(1);
        boolean isDeleted = userRepository.deleteRoleFromUserById(user.getId(), roleToDelete.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertEquals(userRolesExpected, userRepository.findRolesByUserId(user.getId()));
    }
}