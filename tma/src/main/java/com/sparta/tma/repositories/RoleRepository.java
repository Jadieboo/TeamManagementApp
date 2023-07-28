package com.sparta.tma.repositories;

import com.sparta.tma.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleIgnoreCase(String role);

    Role findRoleById(int id);
}