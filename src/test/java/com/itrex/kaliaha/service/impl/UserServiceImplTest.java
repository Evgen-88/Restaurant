package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.UserConverter;
import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.dto.UserUpdateDTO;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

class UserServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;

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
    void findByIdTest_shouldReturnUserDTO() throws ServiceException {
        //given
        UserDTO expected = getUserDTOExpected();

        // when
        when(userRepository.findById(1L)).thenReturn(Optional.of(getUserFindById()));
        UserDTO actual = userService.findById(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllUserListDTO() throws ServiceException {
        //given
        List<UserListDTO> expected = getListUserListDTO();

        // when
        when(userRepository.findAll()).thenReturn(getUsers());
        List<UserListDTO> actual = userService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_validData_shouldReturnUserList() {
        //given
        int expectedSize = 2;
        User user = User.builder().build();
        Pageable pageable = PageRequest.of(1, 2, Sort.by("lastName").descending());

        // when
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(user, user)));
        int actualSize = userService.findAll(pageable).getSize();

        //then
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void addTest_shouldAddNewUser() throws ServiceException {
        //given
        when(userRepository.findAll()).thenReturn(getUsers());
        List<UserListDTO> actualList = userService.findAll();

        Assertions.assertEquals(2, actualList.size());

        // when
        Set<Long> rolesId = new HashSet<>() {{
            add(1L);
            add(3L);
        }};
        UserSaveDTO expected = UserSaveDTO.builder().lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").rolesId(rolesId).build();

        Set<Role> roles = new HashSet<>() {{
            add(Role.builder().id(1L).build());
            add(Role.builder().id(3L).build());
        }};
        User beforeAdd = User.builder().lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").build();
        User afterAdd = User.builder().id(3L).lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").roles(new HashSet<>(roles)).build();

        when(userRepository.save(beforeAdd)).thenReturn(afterAdd);
        UserSaveDTO actual = userService.add(expected);

        //then
        Assertions.assertNotNull(actual.getId());
        expected.setId(3L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest_shouldUpdateUser() throws ServiceException {
        //given
        UserUpdateDTO expected = UserUpdateDTO.builder().id(1L).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha.vladzislav").password("Updated 2222").address("Updated г.Витебск").build();

        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(getUserFindById()));
        UserUpdateDTO actual = userService.update(expected);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteTest_shouldDeleteUser() {
        //given && when && then
        when(userRepository.findById(1L)).thenReturn(Optional.of(getUserFindById()));
        Assertions.assertDoesNotThrow(() -> userService.delete(1L));
    }

    @Test
    void addRoleToUserTest_shouldAddRoleToUser() {
        //given && when && then
        when(userRepository.findById(1L)).thenReturn(Optional.of(getUserFindById()));
        Assertions.assertDoesNotThrow(() -> userService.addRoleToUser(1L, 3L));
    }

    @Test
    void deleteRoleFromUserTest_shouldDeleteRoleFromUser() {
        //given && when && then
        when(userRepository.findById(1L)).thenReturn(Optional.of(getUserFindById()));
        Assertions.assertDoesNotThrow(() -> userService.deleteRoleFromUser(1L, 2L));
    }
}