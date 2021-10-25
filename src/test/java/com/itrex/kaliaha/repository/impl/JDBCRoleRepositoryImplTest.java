package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.Repository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCRoleRepositoryImplTest extends BaseRepositoryTest {
    private final Repository<Role> roleRepository;
    private final List<Role> roles;

    public JDBCRoleRepositoryImplTest() {
        super();
        roleRepository = new JDBCRoleRepositoryImpl(getConnectionPool());
        roles = new ArrayList<>() {{
            add(new Role(1L, "admin"));
            add(new Role(2L, "user"));
            add(new Role(3L,"cook"));
        }};
    }

    @Test
    public void findById_validData_shouldReturnExistRoleById() {
        //given
        Role expected = roles.get(0);
        //when
        Role actual = roleRepository.findById(expected.getId());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnAllExistedRoles() {
        //given && when
        List<Role> actual = roleRepository.findAll();
        //then
        Assert.assertEquals(roles, actual);
    }

    @Test
    public void add_validData_shouldReturnNewRole() {
        //given
        Role expected = new Role(4L, "check");
        //when
        Role actual = new Role("check");
        roleRepository.add(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addAll_shouldAddNewRoles() {
        //given
        List<Role> expected = new ArrayList<>() {{
            add(new Role(4L, "middle admin"));
            add(new Role(5L, "middle user"));
        }};
        //when
        List<Role> actual = new ArrayList<>() {{
            add(new Role("middle admin"));
            add(new Role("middle user"));
        }};
        roleRepository.addAll(actual);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void update_shouldUpdateFirstRole() {
        //given
        Role expected = new Role(1L, "middle admin");
        //when
        boolean result = roleRepository.update(expected);
        //then
        Assert.assertTrue(result);
    }

    @Test
    public void delete_shouldDeleteFirstRole() {
        //given
        Role newRole = new Role("senior admin");
        roleRepository.add(newRole);
        Role checkAdd = new Role(4L, "senior admin");
        Assert.assertEquals(checkAdd, newRole);
        //when
        boolean actual = roleRepository.delete(newRole.getId());
        //then
        Assert.assertTrue(actual);
    }
}