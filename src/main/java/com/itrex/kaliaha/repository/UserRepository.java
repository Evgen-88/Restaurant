package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;

import java.util.List;

public interface UserRepository extends BaseRepository<User> {
    List<Role> findRolesByUserId(Long userId);
    boolean deleteRoleFromUserById(Long userId, Long roleId);
}