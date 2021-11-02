package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImplTest extends BaseRepositoryTest {
    private final RoleRepository roleRepository;
    private final List<Role> roles;

    public RoleRepositoryImplTest() {
        roleRepository = getApplicationContext().getBean(RoleRepositoryImpl.class);
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
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingRoles() {
        //given && when
        List<Role> actual = roleRepository.findAll();

        //then
        Assert.assertEquals(roles, actual);
    }

    @Test
    public void addTest_validData_shouldReturnNewRole() {
        //given
        List<Role> expected = roleRepository.findAll();

        Assert.assertEquals(roles.size(), expected.size());

        //when
        Role newActual = new Role( "check");
        boolean isAdded = roleRepository.add(newActual);
        Role newExpected = new Role(4L, "check");
        expected.add(newExpected);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(newExpected, newActual);
        Assert.assertEquals(newExpected, roleRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_shouldUpdateRole() {
        //given
        Role expected = new Role(1L, "middle admin");
        Role actual = roleRepository.findById(1L);

        Assert.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setRoleName("middle admin");
        boolean isUpdated = roleRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, roleRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteRole() {
        //given
        Role expected = roles.get(1) ;
        Role actual = roleRepository.findById(2L);
        List<User> users = roleRepository.findAllUsersWhoHaveRoleById(actual.getId());

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(3, users.size());

        //when
        boolean isDeleted = roleRepository.delete(actual.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(roleRepository.findById(2L));
        Assert.assertEquals(0, roleRepository.findAllUsersWhoHaveRoleById(actual.getId()).size());
    }

    @Test
    public void findAllUsersWhoHaveRoleByIdTest_validData_shouldReturnAllExistingRoles() {
        //given && when
        List<User> actual = roleRepository.findAllUsersWhoHaveRoleById(2L);

        //then
        Assert.assertEquals(3, actual.size());
    }
}