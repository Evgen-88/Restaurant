package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;

import java.util.List;

public interface UserRepository extends BaseRepository<User> {
    User add(User user, List<Role> roles);
    List<Role> findRolesByUserId(Long userId);
    boolean deleteRoleFromUserById(Long userId, Long roleId);
    User findWithRolesAndOrdersById(Long userId);
}