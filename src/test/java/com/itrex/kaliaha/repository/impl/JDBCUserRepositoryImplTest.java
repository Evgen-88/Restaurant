package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCUserRepositoryImplTest extends BaseRepositoryTest {
    private final UserRepository userRepository;
    private final List<User> users;

    public JDBCUserRepositoryImplTest() {
        super();
        userRepository = new JDBCUserRepositoryImpl(getConnectionPool());
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
    public void add_validData_shouldAddNewUserAndSetDefaultRole() {
        //given
        User expected = new User(5L, "Сидоров", "Александр", "suitor", "5555", "г.Минск");
        List<Role> userRolesBeforeAdding = userRepository.findRolesByUserId(5L);
        Assert.assertEquals(0, userRolesBeforeAdding.size());
        //when
        User actual = new User("Сидоров", "Александр", "suitor", "5555", "г.Минск");
        userRepository.add(actual);
        //then
        Assert.assertEquals(expected, actual);
        List<Role> userRolesAfterAdding = userRepository.findRolesByUserId(5L);
        Assert.assertEquals(1, userRolesAfterAdding.size());
    }

    @Test
    public void addAll_validData_shouldAddAllUsers() {
        //given
        List<User> expected = new ArrayList<>() {{
            add(new User(5L, "Черкасов", "Владимир", "checks", "5555", "г.Минск"));
            add(new User(6L, "Пирожков", "Павел", "prior", "666", "г.Минск"));
        }};
        //when
        List<User> actual = new ArrayList<>() {{
            add(new User("Черкасов", "Владимир", "checks", "5555", "г.Минск"));
            add(new User("Пирожков", "Павел", "prior", "666", "г.Минск"));
        }};
        userRepository.addAll(actual);
        //then
        assertEquals(expected, actual);
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
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete_validData_shouldDeleteUser() {
        //given
        Long userId = users.get(0).getId();
        OrderRepository orderRepository = new JDBCOrderRepositoryImpl(getConnectionPool());
        DishRepository dishRepository = new JDBCDishRepositoryImpl(getConnectionPool());

        List<Role> userRolesBeforeDelete = userRepository.findRolesByUserId(userId);
        Assert.assertEquals(2, userRolesBeforeDelete.size());

        List<Order> remainderOrdersBeforeDelete = orderRepository.findOrdersByUserId(userId);
        Assert.assertEquals(3, remainderOrdersBeforeDelete.size());

        List<Dish> userOrderedDishesBeforeDelete = dishRepository.findAllDishesInOrderById(1L);
        Assert.assertEquals(3, userOrderedDishesBeforeDelete.size());
        //when
        boolean actual = userRepository.delete(userId);
        //then
        List<Order> remainderUserOrdersAfterDelete = orderRepository.findOrdersByUserId(userId);
        Assert.assertEquals(0, remainderUserOrdersAfterDelete.size());

        List<Dish> dishesAfterDelete = dishRepository.findAllDishesInOrderById(1L);
        Assert.assertEquals(0, dishesAfterDelete.size());

        List<Role> userRolesAfterDelete = userRepository.findRolesByUserId(userId);
        Assert.assertEquals(0, userRolesAfterDelete.size());

        Assert.assertTrue(actual);
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
        //given && when && then
        Assert.assertTrue(userRepository.deleteRoleFromUserById(1L, 2L));
    }
}