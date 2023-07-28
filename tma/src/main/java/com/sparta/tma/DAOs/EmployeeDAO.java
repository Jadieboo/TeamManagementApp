package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.EmployeeRepository;

public class EmployeeDAO {
    private EmployeeRepository repo;


    public EmployeeDAO(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee newEmployee(EmployeeDTO jsonBody) {
        Employee newEmployee = new Employee();
        newEmployee.setId(0);
        newEmployee.setFirstName(jsonBody.getFirstName());
        newEmployee.setLastName(jsonBody.getLastName());

        return newEmployee;

    }

}
