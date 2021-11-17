package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.AddMethodUserRepositoryImplException;
import com.itrex.kaliaha.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserRepositoryImplTest extends BaseRepositoryTest {
    private final List<User> users;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private RoleRepository roleRepository;

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
        List<Role> roles = new ArrayList<>() {{add(Role.builder().id(1L).build());add(Role.builder().id(2L).build());}};

        Assertions.assertEquals(users.size(), expected.size());

        //when
        User newActual = User.builder().lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").build();
        User addedUser = userRepository.add(newActual, roles);
        User newExpected = User.builder().id(5L).lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedUser.getId());
        Assertions.assertEquals(newExpected, newActual);
        Assertions.assertEquals(newExpected, userRepository.findById(newActual.getId()));
        Assertions.assertEquals(roles.size(), roleRepository.findRolesByUserId(newActual.getId()).size());
    }

    @Test
    public void updateTest_validData_shouldUpdateUser() {
        //given
        User expected = User.builder().id(1L).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha").password("Updated 2222").address("Updated г.Минск").build();
        User actual = userRepository.findById(expected.getId());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = User.builder().id(expected.getId()).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha").password("Updated 2222").address("Updated г.Минск").build();
        User updatedUser = userRepository.update(actual);

        //then
        Assertions.assertEquals(expected, updatedUser);
        Assertions.assertEquals(expected, userRepository.findById(updatedUser.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteUser() {
        //given
        User expected = users.get(0);
        User actual = userRepository.findById(1L);
        List<Order> actualOrders = orderRepository.findOrdersByUserId(actual.getId());
        List<Dish> orderedDishes = dishRepository.findAllDishesInOrderById(actualOrders.get(0).getId());

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(2, roleRepository.findRolesByUserId(actual.getId()).size());
        Assertions.assertEquals(3, actualOrders.size());

        //when
        boolean isDeleted = userRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(userRepository.findById(1L));
        Assertions.assertEquals(0, roleRepository.findRolesByUserId(actual.getId()).size());
        Assertions.assertEquals(0, orderRepository.findOrdersByUserId(actual.getId()).size());
        Assertions.assertNotNull(dishRepository.findById(orderedDishes.get(0).getId()));
        Assertions.assertNotNull(dishRepository.findById(orderedDishes.get(1).getId()));
    }

    @Test
    public void findAllUsersWhoHaveRoleByIdTest_validData_shouldReturnAllExistingRoles() {
        //given && when
        List<User> actual = userRepository.findAllUsersWhoHaveRoleById(2L);

        //then
        Assertions.assertEquals(3, actual.size());
    }

    @Test
    public void addRoleToUserTest_validData_shouldAddRoleToUser() {
        //given
        User user = userRepository.findById(2L);
        Set<Role> actual = roleRepository.findRolesByUserId(user.getId());

        Assertions.assertEquals(1, actual.size());

        //when
        boolean isAdded = userRepository.addRoleToUser(2L, 1L);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(2, roleRepository.findRolesByUserId(2L).size());
    }

    @Test
    public void deleteRoleFromUserTest_validData_shouldDeleteUserRole() {
        //given
        User user = userRepository.findById(1L);
        Role role = Role.builder().id(1L).roleName("admin").build();
        Set<Role> userRolesExpected = roleRepository.findRolesByUserId(user.getId());

        Assertions.assertEquals(2, userRolesExpected.size());

        //when
        userRolesExpected.remove(role);
        boolean isDeleted = userRepository.deleteRoleFromUser(user.getId(), role.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(userRolesExpected, roleRepository.findRolesByUserId(user.getId()));
    }
}