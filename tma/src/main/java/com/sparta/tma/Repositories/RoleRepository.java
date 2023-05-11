package com.sparta.tma.Repositories;

import com.sparta.tma.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleIgnoreCase(String role);

    Role findRoleById(int id);
}