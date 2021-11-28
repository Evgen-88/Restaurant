package com.itrex.kaliaha.repository.deprecated.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.RepositoryException;
import com.itrex.kaliaha.repository.deprecated.BaseRepository;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.deprecated.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImplTest extends BaseRepositoryTest {
    private final List<Role> roles;
    @Autowired
    private BaseRepository<Role> roleRepository;
    @Autowired
    private UserRepository userRepository;

    public RoleRepositoryImplTest() {
        roles = new ArrayList<>() {{
            add(Role.builder().id(1L).roleName("admin").build());
            add(Role.builder().id(2L).roleName("user").build());
            add(Role.builder().id(3L).roleName("cook").build());
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnExistRoleById() throws RepositoryException {
        //given
        Role expected = roles.get(0);

        //when
        Role actual = roleRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingRoles() throws RepositoryException {
        //given && when
        List<Role> actual = roleRepository.findAll();

        //then
        Assertions.assertEquals(roles, actual);
    }

    @Test
    public void addTest_validData_shouldReturnNewRole() throws RepositoryException {
        //given
        List<Role> expected = roleRepository.findAll();

        Assertions.assertEquals(roles.size(), expected.size());

        //when
        Role newActual = Role.builder().roleName("check").build();
        Role addedRole = roleRepository.add(newActual);
        Role newExpected = Role.builder().id(4L).roleName("check").build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedRole.getId());
        Assertions.assertEquals(newExpected, addedRole);
        Assertions.assertEquals(newExpected, roleRepository.findById(addedRole.getId()));
    }

    @Test
    public void updateTest_shouldUpdateRole() throws RepositoryException {
        //given
        Role expected = Role.builder().id(1L).roleName("middle admin").build();
        Role actual = roleRepository.findById(1L);

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = Role.builder().id(expected.getId()).roleName("middle admin").build();
        Role updatedRole = roleRepository.update(actual);

        //then
        Assertions.assertEquals(expected, updatedRole);
        Assertions.assertEquals(expected, roleRepository.findById(updatedRole.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteRole() throws RepositoryException {
        //given
        Role expected = roles.get(1);
        Role actual = roleRepository.findById(2L);
        List<User> users = userRepository.findAllUsersWhoHaveRoleById(actual.getId());

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(3, users.size());

        //when
        boolean isDeleted = roleRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(roleRepository.findById(2L));
        Assertions.assertEquals(0, userRepository.findAllUsersWhoHaveRoleById(actual.getId()).size());
    }
}