package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRepositoryTest extends BaseRepositoryTest {
    private final List<User> users;
    @Autowired
    private UserRepository userRepository;

    public UserRepositoryTest() {
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
        User actual = userRepository.findById(expected.getId()).orElse(null);

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
    public void addTest_validData_shouldAddNewUserWithRoles() {
        //given
        List<User> expected = userRepository.findAll();
        Set<Role> roles = new HashSet<>() {{add(Role.builder().id(1L).build());add(Role.builder().id(2L).build());}};

        Assertions.assertEquals(users.size(), expected.size());

        //when
        User newActual = User.builder().lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").build();
        newActual.setRoles(roles);
        User addedUser = userRepository.save(newActual);
        User newExpected = User.builder().id(5L).lastName("Сидоров").firstName("Александр").login("suitor").password("5555").address("г.Минск").build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedUser.getId());
        Assertions.assertEquals(newExpected, newActual);
        Assertions.assertEquals(newExpected, userRepository.findById(newActual.getId()).orElse(null));
        Assertions.assertEquals(roles.size(), userRepository.findRolesByUserId(newActual.getId()).size());
    }

    @Test
    public void updateTest_validData_shouldUpdateUser() {
        //given
        User expected = User.builder().id(1L).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha").password("Updated 2222").address("Updated г.Минск").build();
        User actual = userRepository.findById(expected.getId()).orElse(User.builder().build());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = User.builder().id(expected.getId()).lastName("Updated Коляго").firstName("Updated Владислав").login("Updated kaliaha").password("Updated 2222").address("Updated г.Минск").build();
        User updatedUser = userRepository.save(actual);

        //then
        Assertions.assertEquals(expected, updatedUser);
        Assertions.assertEquals(expected, userRepository.findById(updatedUser.getId()).orElse(null));
    }

    @Test
    public void findAllUsersWhoHaveRoleByIdTest_validData_shouldReturnAllExistingRoles() {
        //given && when
        List<User> actual = userRepository.findUsersWhoHaveRoleById(2L);

        //then
        Assertions.assertEquals(4, actual.size());
    }



    @Test
    public void findRolesByUserIdTest_validData_shouldReturnUserRoles() {
        //given
        User user = userRepository.findById(1L).orElse(User.builder().build());

        // when
        Set<Role> actual = userRepository.findRolesByUserId(user.getId());

        //then
        Assertions.assertEquals(2, actual.size());
    }
}