package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.repository.RoleRepository;
import com.itrex.kaliaha.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

class RoleServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private RoleServiceImpl roleService;
    @Mock
    private RoleRepository roleRepository;

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