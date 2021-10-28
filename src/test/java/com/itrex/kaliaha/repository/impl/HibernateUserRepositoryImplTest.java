package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.*;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HibernateUserRepositoryImplTest extends BaseRepositoryTest {
    private final UserRepository userRepository;
    private final List<User> users;

    public HibernateUserRepositoryImplTest() {
        super();
        userRepository = new HibernateUserRepositoryImpl();
        users = new ArrayList<>() {{
            add(new User(1L, "Коляго", "Владислав", "kaliaha.vladzislav", "1111", "г.Витебск"));
            add(new User(2L, "Молочко", "Юрий", "molochko.urey", "2222", "г.Хойники"));
            add(new User(3L,"Рубанов", "Владислав", "rubanov", "3333", "г.Жлобин"));
            add(new User(4L, "Петров", "Сергей", "petrov", "4444", "г.Москва"));
        }};
    }

    @Test
    public void findById_validData_shouldReturnUserById() {
        //given
        User expected = users.get(0);
        //when
        User actual = userRepository.findById(expected.getId());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnAllUsers() {
        //given && when
        List<User> actual = userRepository.findAll();
        //then
        Assert.assertEquals(users, actual);
    }

    @Test
    public void add_validData_shouldAddNewUser() {
        //given
        User expected = new User(5L, "Сидоров", "Александр", "suitor", "5555", "г.Минск");
        User actual = new User("Сидоров", "Александр", "suitor", "5555", "г.Минск");

        //when
        boolean isAdded = userRepository.add(actual, new ArrayList<>() {{add(new Role(1L)); add(new Role(2L));}});

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(actual, userRepository.findById(actual.getId()));
        Assert.assertEquals(2, userRepository.findRolesByUserId(actual.getId()).size());
    }

    @Test
    public void addAll_validData_shouldAddAllUsers() {
        //given
        List<User> expected = new ArrayList<>() {{
            add(new User(5L, "Черкасов", "Владимир", "checks", "5555", "г.Минск"));
            add(new User(6L, "Пирожков", "Павел", "prior", "666", "г.Минск"));
        }};
        List<User> actual = new ArrayList<>() {{
            add(new User("Черкасов", "Владимир", "checks", "5555", "г.Минск"));
            add(new User("Пирожков", "Павел", "prior", "666", "г.Минск"));
        }};

        //when
        boolean isAdded = userRepository.addAll(actual, new ArrayList<>() {{add(new Role(1L)); add(new Role(2L));}});

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(expected, actual);

        Assert.assertEquals(actual.get(0), userRepository.findById(actual.get(0).getId()));
        Assert.assertEquals(2, userRepository.findRolesByUserId(actual.get(0).getId()).size());

        Assert.assertEquals(actual.get(1), userRepository.findById(actual.get(1).getId()));
        Assert.assertEquals(2, userRepository.findRolesByUserId(actual.get(1).getId()).size());
    }

    @Test
    public void update_validData_shouldUpdateUser() {
        //given
        User actual = users.get(0);
        User expected = new User(1L, "Updated Коляго", "Updated Владислав", "Updated kaliaha", "Updated 2222", "Updated г.Минск");
        //when
        actual.setLastName("Updated Коляго");
        actual.setFirstName("Updated Владислав");
        actual.setLogin("Updated kaliaha");
        actual.setPassword("Updated 2222");
        actual.setAddress("Updated г.Минск");
        userRepository.update(actual);
        //then
        Assert.assertEquals(expected, userRepository.findById(1L));
    }

    @Test
    public void delete_validData_shouldDeleteUser() {
        OrderRepository orderRepository = new HibernateOrderRepositoryImpl();
        DishRepository dishRepository = new HibernateDishRepositoryImpl();
        //given
        User expected = users.get(0);
        User actual = userRepository.findById(1L);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(2, userRepository.findRolesByUserId(actual.getId()).size());
        List<Order> actualOrders = orderRepository.findOrdersByUserId(actual.getId());
        Assert.assertEquals(3, actualOrders.size());

        List<Dish> orderedDishes =  dishRepository.findAllDishesInOrderById(actualOrders.get(0).getId());
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
    public void findUserRolesById__validData_shouldReturnUserRoles() {
        //given
        List<Role> expected = new ArrayList<>() {{
            add(new Role(1L, "admin"));
            add(new Role(2L, "user"));
        }};
        //when
        List<Role> actual = userRepository.findRolesByUserId(1L);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteRoleFromUserById__validData_shouldDeleteUserRole() {
        List<Role> expected = userRepository.findRolesByUserId(1L);
        expected.remove(new Role(2L, "user"));
        List<Role> rolesExpected = new ArrayList<>(expected);
        //given && when && then
        Assert.assertTrue(userRepository.deleteRoleFromUserById(1L, 2L));
        List<Role> actual = userRepository.findRolesByUserId(1L);
        List<Role> rolesActual = new ArrayList<>(actual);
        Assert.assertEquals(rolesExpected, rolesActual);
    }

}