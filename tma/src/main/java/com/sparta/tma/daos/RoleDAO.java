package com.sparta.tma.daos;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Role;

public class RoleDAO {
    public Role getRole(EmployeeDTO employeeDetails) throws NullPointerException, IllegalArgumentException {

        if(employeeDetails.getRole() == null) throw new NullPointerException("Role not found: field is empty");

        String role = employeeDetails.getRole()
                .trim()
                .toUpperCase();

        return switch (role) {
            case "ADMIN" -> Role.ADMIN;
            case "MANAGER" -> Role.MANAGER;
            case "EMPLOYEE" -> Role.EMPLOYEE;
            default -> throw new IllegalArgumentException("Role not found, please enter a valid role (Admin/Manager/Employee)");
        };
    }
}
