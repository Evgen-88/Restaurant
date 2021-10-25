package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;

import java.util.List;

public interface UserRepository extends Repository<User> {
    List<Role> findRolesByUserId(Long userId);
    boolean deleteRoleByUserId(Long userId, Long roleId);


}