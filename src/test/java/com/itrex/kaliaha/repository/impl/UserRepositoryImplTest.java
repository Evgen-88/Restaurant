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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
            add(User.builder().id(1L).lastName("Коляго").firstName("Владислав").login("kaliaha.vladzislav").password("1111").address("г.Витебск").build());
            add(User.builder().id(2L).lastName("Молочко").firstName("Юрий").login("molochko.urey").password("2222").address("г.Хойники").build());
            add(User.builder().id(3L).lastName("Рубанов").firstName("Владислав").login("rubanov").password("3333").address("г.Жлобин").build());
            add(User.builder().id(4L).lastName("Петров").firstName("Сергей").login("petrov").password("4444").address("г.Москва").build());
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
            add(Role.builder().id(1L).build());
            add(Role.builder().id(2L).build());
        }};

        Assertions.assertEquals(users.size(), expected.size());

        //when
        User newActual = User.builder().lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").build();
        boolean isAdded = userRepository.add(newActual, roles);
        User newExpected = User.builder().id(5L).lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").build();
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
        User expected = User.builder().id(1L).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha").password("Updated 2222").address("Updated г.Минск").build();
        User actual = userRepository.findById(expected.getId());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = User.builder().id(expected.getId()).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha").password("Updated 2222").address("Updated г.Минск").build();
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