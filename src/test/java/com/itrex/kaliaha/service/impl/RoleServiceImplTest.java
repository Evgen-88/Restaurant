package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.repository.BaseRepository;
import com.itrex.kaliaha.service.BaseServiceTest;
import com.itrex.kaliaha.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

class RoleServiceImplTest extends BaseServiceTest {
    @Autowired
    private RoleService roleService;
    @MockBean
    private BaseRepository<Role> roleRepository;

    @Test
    void findAllTest_shouldReturnAllRoleDTO() {
        //given
        List<RoleDTO> expected = getListRoleDTO();

        // when
        Mockito.when(roleRepository.findAll()).thenReturn(getRoles());
        List<RoleDTO> actual = roleService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }
}