package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Department;
import com.sparta.tma.repositories.DepartmentRepository;

public class DepartmentDAO {

    private DepartmentRepository repo;

    public DepartmentDAO(DepartmentRepository repo) {
        this.repo = repo;
    }

    public Department getDepartmentDao(EmployeeDTO jsonBody) {
        return repo.findByDepartmentIgnoreCase(jsonBody.getDepartment());
    }
}
