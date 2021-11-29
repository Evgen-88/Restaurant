package com.itrex.kaliaha.repository.hibernate;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.RepositoryException;

import java.util.List;
import java.util.Set;

@Deprecated
public interface UserRepository extends BaseRepository<User> {
    User add(User user, Set<Role> roles) throws RepositoryException;
    Set<Role> findRolesByUserId(Long userId) throws RepositoryException;
    List<User> findAllUsersWhoHaveRoleById(Long roleId) throws RepositoryException;
    boolean addRoleToUser(Long userId, Long roleId) throws RepositoryException;
    boolean deleteRoleFromUser(Long userId, Long roleId) throws RepositoryException;
}