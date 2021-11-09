package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleConverter {
    public static RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static List<RoleDTO> toRoleListDTO(List<Role> roles) {
        return roles.stream()
                .map(RoleConverter::toDTO)
                .collect(Collectors.toList());
    }

    public static Role fromDTO(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }

    public static List<Role> fromRoleListDTO(List<RoleDTO> roles) {
        return roles.stream()
                .map(RoleConverter::fromDTO)
                .collect(Collectors.toList());
    }

    public static Role fromDTO(Long id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }

    public static List<Role> fromRoleListIdDTO(List<Long> rolesId) {
        return rolesId.stream()
                .map(RoleConverter::fromDTO)
                .collect(Collectors.toList());
    }
}
