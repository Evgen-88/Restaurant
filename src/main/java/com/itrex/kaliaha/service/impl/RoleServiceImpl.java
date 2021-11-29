package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.RoleConverter;
import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.RoleRepository;
import com.itrex.kaliaha.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() throws ServiceException {
        return RoleConverter.toRoleListDTO(roleRepository.findAll());
    }
}