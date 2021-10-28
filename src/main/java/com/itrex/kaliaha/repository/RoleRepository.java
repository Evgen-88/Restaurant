package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;

import java.util.List;

public interface RoleRepository extends BaseRepository<Role>{
    List<User> findAllUsersWhoHaveRoleById(Long id);
}
