package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.RoleRepository;
import com.itrex.kaliaha.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;

class RoleServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private RoleServiceImpl roleService;
    @Mock
    private RoleRepository roleRepository;

    @Test
    void findAllTest_shouldReturnAllRoleDTO() throws ServiceException {
        //given
        List<RoleDTO> expected = getListRoleDTO();

        // when
        when(roleRepository.findAll()).thenReturn(getRoles());
        List<RoleDTO> actual = roleService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }
}