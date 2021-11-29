package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface RoleRepository extends JpaRepository<Role, Long> {}