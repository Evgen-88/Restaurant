package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    private final List<Role> roles;

    public RoleRepositoryImplTest() {
        roles = new ArrayList<>() {{
            add(new Role(1L, "admin"));
            add(new Role(2L, "user"));
            add(new Role(3L,"cook"));
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnExistRoleById() {
        //given
        Role expected = roles.get(0);

        //when
        Role actual = roleRepository.findById(expected.getId());

        //then
       Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingRoles() {
        //given && when
        List<Role> actual = roleRepository.findAll();

        //then
       Assertions.assertEquals(roles, actual);
    }

    @Test
    public void addTest_validData_shouldReturnNewRole() {
        //given
        List<Role> expected = roleRepository.findAll();

       Assertions.assertEquals(roles.size(), expected.size());

        //when
        Role newActual = new Role( "check");
        boolean isAdded = roleRepository.add(newActual);
        Role newExpected = new Role(4L, "check");
        expected.add(newExpected);

        //then
        Assertions.assertTrue(isAdded);
       Assertions.assertEquals(newExpected, newActual);
       Assertions.assertEquals(newExpected, roleRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_shouldUpdateRole() {
        //given
        Role expected = new Role(1L, "middle admin");
        Role actual = roleRepository.findById(1L);

       Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setRoleName("middle admin");
        boolean isUpdated = roleRepository.update(actual);

        //then
        Assertions.assertTrue(isUpdated);
       Assertions.assertEquals(expected, actual);
       Assertions.assertEquals(expected, roleRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteRole() {
        //given
        Role expected = roles.get(1) ;
        Role actual = roleRepository.findById(2L);
        List<User> users = roleRepository.findAllUsersWhoHaveRoleById(actual.getId());

       Assertions.assertEquals(expected, actual);
       Assertions.assertEquals(3, users.size());

        //when
        boolean isDeleted = roleRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(roleRepository.findById(2L));
       Assertions.assertEquals(0, roleRepository.findAllUsersWhoHaveRoleById(actual.getId()).size());
    }

    @Test
    public void findAllUsersWhoHaveRoleByIdTest_validData_shouldReturnAllExistingRoles() {
        //given && when
        List<User> actual = roleRepository.findAllUsersWhoHaveRoleById(2L);

        //then
       Assertions.assertEquals(3, actual.size());
    }
}