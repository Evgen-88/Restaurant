package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;

import java.util.List;
import java.util.Set;

public interface UserRepository extends BaseRepository<User> {
    User add(User user, List<Role> roles);
    Set<Role> findRolesByUserId(Long userId);
    List<User> findAllUsersWhoHaveRoleById(Long roleId);
    boolean addRoleToUser(Long userId, Long roleId);
    boolean deleteRoleFromUser(Long userId, Long roleId);
}