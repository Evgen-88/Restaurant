package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.RoleConverter;
import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.deprecated.BaseRepository;
import com.itrex.kaliaha.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final BaseRepository<Role> roleRepository;

    public RoleServiceImpl(BaseRepository<Role> roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> findAll() throws ServiceException {
        try {
            return RoleConverter.toRoleListDTO(roleRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }
}