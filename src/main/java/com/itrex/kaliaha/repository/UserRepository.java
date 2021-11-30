package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Primary
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u left join fetch u.orders left join fetch u.roles where u.id =:id")
    Optional<User> findById(Long id);

    @Query("from Role r left join fetch r.users u where u.id =:userId")
    Set<Role> findRolesByUserId(Long userId);

    @Query("from User u left join fetch u.roles r where r.id =:roleId")
    List<User> findUsersWhoHaveRoleById(Long roleId);

    @Query("from User u left join fetch u.roles where u.login =:login")
    Optional<User> findByLogin(String login);
}