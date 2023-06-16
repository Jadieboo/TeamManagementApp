package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.Entities.Employee;
import com.sparta.tma.Entities.Role;
import com.sparta.tma.Repositories.RoleRepository;

public class RoleDAO {

    private RoleRepository repo;

    public RoleDAO(RoleRepository repo) {
        this.repo = repo;
    }

    public Role getRoleDao(EmployeeDTO jsonBody) {
        return repo.findRoleByRoleIgnoreCase(jsonBody.getRole());
    }
}
