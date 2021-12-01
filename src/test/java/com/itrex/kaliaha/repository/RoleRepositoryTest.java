package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryTest extends BaseRepositoryTest {
    private final List<Role> roles;
    @Autowired
    private RoleRepository roleRepository;

    public RoleRepositoryTest() {
        roles = new ArrayList<>() {{
            add(Role.builder().id(1L).roleName("admin").build());
            add(Role.builder().id(2L).roleName("user").build());
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnExistRoleById() {
        //given
        Role expected = roles.get(0);

        //when
        Role actual = roleRepository.findById(expected.getId()).orElse(null);

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
        Role newActual = Role.builder().roleName("check").build();
        Role addedRole = roleRepository.save(newActual);
        Role newExpected = Role.builder().id(3L).roleName("check").build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedRole.getId());
        Assertions.assertEquals(newExpected, addedRole);
        Assertions.assertEquals(newExpected, roleRepository.findById(addedRole.getId()).orElse(null));
    }

    @Test
    public void updateTest_shouldUpdateRole() {
        //given
        Role expected = Role.builder().id(1L).roleName("middle admin").build();
        Role actual = roleRepository.findById(1L).orElse(Role.builder().build());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = Role.builder().id(expected.getId()).roleName("middle admin").build();
        Role updatedRole = roleRepository.save(actual);

        //then
        Assertions.assertEquals(expected, updatedRole);
        Assertions.assertEquals(expected, roleRepository.findById(updatedRole.getId()).orElse(null));
    }
}