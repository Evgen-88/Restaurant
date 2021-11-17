package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.RoleConverter;
import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.repository.BaseRepository;
import com.itrex.kaliaha.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final BaseRepository<Role> roleRepository;

    public RoleServiceImpl(BaseRepository<Role> roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> findAll() {
        return RoleConverter.toRoleListDTO(roleRepository.findAll());
    }
}