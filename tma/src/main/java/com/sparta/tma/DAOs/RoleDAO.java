package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Role;

public class RoleDAO {
    public Role getRole(EmployeeDTO employeeDetails) {
        String role = employeeDetails.getRole()
                .trim()
                .toUpperCase();

        return switch (role) {
            case "ADMIN" -> Role.ADMIN;
            case "MANAGER" -> Role.MANAGER;
            case "EMPLOYEE" -> Role.EMPLOYEE;
            default -> throw new IllegalArgumentException("Role not found");
        };
    }
}
