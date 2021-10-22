package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;

import java.util.List;

public interface RoleRepository {
    Role selectById(Long id);
    List<Role> selectAll();
    void add(Role role);
    void addAll(List<Role> roles);
    boolean update(Role role);
    boolean remove(Long id);
}