package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;

import java.util.List;

public interface UserRepository {
    User findById(Long id);
    List<User> findAll();
    boolean add(User user, List<Role> roles);
    boolean addAll(List<User> users, List<Role> roles);
    boolean update(User user);
    boolean delete(Long id);
    List<Role> findRolesByUserId(Long userId);
    boolean deleteRoleFromUserById(Long userId, Long roleId);
}