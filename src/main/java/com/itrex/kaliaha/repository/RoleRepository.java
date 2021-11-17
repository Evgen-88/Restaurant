package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;

import java.util.Set;

public interface RoleRepository extends BaseRepository<Role>{
    Set<Role> findRolesByUserId(Long userId);
}