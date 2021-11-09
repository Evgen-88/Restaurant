package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.RoleConverter;
import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.repository.RoleRepository;
import com.itrex.kaliaha.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> findAll() {
        List<RoleDTO> rolesDTO = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            rolesDTO.add(RoleConverter.toDTO(role));
        }
        return rolesDTO;
    }
}