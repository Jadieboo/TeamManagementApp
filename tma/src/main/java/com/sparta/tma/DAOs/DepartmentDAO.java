package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Department;
import com.sparta.tma.repositories.DepartmentRepository;

public class DepartmentDAO {

    private DepartmentRepository repo;

    public DepartmentDAO(DepartmentRepository repo) {
        this.repo = repo;
    }

    public Department getDepartment(EmployeeDTO employeeDetails) {
        // TODO: handle if employeeDetails.getDepartment() is empty

        try {
            repo.findByDepartmentIgnoreCase(employeeDetails.getDepartment());
        } catch (NullPointerException nullPointerException) {
            nullPointerException.getMessage();
        }
        if (employeeDetails.getProject().isEmpty()) throw new NullPointerException();

        return ;
    }
}
