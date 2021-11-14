package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.UserConverter;
import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.BaseServiceTest;
import com.itrex.kaliaha.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class UserServiceImplTest extends BaseServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public User getUserFindById() {
        Set<Role> roles = new HashSet<>() {{
            add(getRoles().get(0));
            add(getRoles().get(1));
        }};
        Set<Order> orders = getOrders().stream()
                .filter(order -> order.getUser().getId() == 1L)
                .collect(Collectors.toSet());
        User user = getUsers().get(0);
        user.setRoles(roles);
        user.setOrders(orders);
        return user;
    }

    public UserDTO getUserDTOExpected() {
        return UserConverter.toDTO(getUserFindById());
    }

    @Test
    void findById() {
        //given
        UserDTO expected = getUserDTOExpected();

        // when
        Mockito.when(userRepository.findById(1L)).thenReturn(getUserFindById());
        UserDTO actual = userService.findById(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_validData_shouldReturnAllExistingUsers() {
        //given
        List<UserListDTO> expected = getListUserListDTO();

        // when
        Mockito.when(userRepository.findAll()).thenReturn(getUsers());
        List<UserListDTO> actual = userService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add() {
        //given
        Mockito.when(userRepository.findAll()).thenReturn(getUsers());
        List<UserListDTO> actualList = userService.findAll();

        Assertions.assertEquals(2, actualList.size());

        // when
        List<Long> rolesId = new ArrayList<>() {{
            add(1L);
            add(3L);
        }};
        UserSaveDTO expected = UserSaveDTO.builder().lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").rolesId(rolesId).build();

        List<Role> roles = new ArrayList<>() {{
            add(Role.builder().id(1L).build());
            add(Role.builder().id(3L).build());
        }};
        User beforeAdd = User.builder().lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").build();
        User afterAdd = User.builder().id(3L).lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").roles(new HashSet<>(roles)).build();

        Mockito.when(userRepository.add(beforeAdd, roles)).thenReturn(afterAdd);
        UserSaveDTO actual = userService.add(expected);

        //then
        Assertions.assertNotNull(actual.getId());
        expected.setId(3L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws ServiceException {
        //given
        UserUpdateDTO expected = UserUpdateDTO.builder().id(1L).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha.vladzislav").password("Updated 2222").address("Updated г.Витебск").build();
        User toUpdate = User.builder().id(1L).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha.vladzislav").password("Updated 2222").address("Updated г.Витебск").build();

        //when
        Mockito.when(userRepository.update(toUpdate)).thenReturn(toUpdate);
        UserUpdateDTO actual = userService.update(expected);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        //given && when && then
        Mockito.when(userRepository.delete(1L)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> userRepository.delete(1L));
    }
}