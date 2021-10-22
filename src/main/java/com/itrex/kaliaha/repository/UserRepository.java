package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;

import java.util.List;

public interface UserRepository {
    User selectById(Long id);
    List<User> selectAll();
    void add(User user);
    void addAll(List<User> users);
    boolean update(User user);
    boolean remove(Long id);

    List<Role> findUserRolesById(Long userId);
    boolean removeUserRoleById(Long userId, Long roleId);
}