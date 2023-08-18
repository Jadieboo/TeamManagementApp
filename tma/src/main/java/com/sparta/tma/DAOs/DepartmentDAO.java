package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Project;
import com.sparta.tma.repositories.DepartmentRepository;

import java.util.HashMap;

public class DepartmentDAO {

    private DepartmentRepository departmentRepository;

    public DepartmentDAO(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department getDepartment(EmployeeDTO employeeDetails) {
        // TODO: handle if employeeDetails.getDepartment() is empty

        if (employeeDetails.getDepartment() == null) throw new NullPointerException("Error: Department field is null");

        String department = employeeDetails.getDepartment().trim().toLowerCase();

        HashMap<Integer, String> departmentsMap = new HashMap<>();

        for ( Department d : departmentRepository.findAll()) {
            departmentsMap.put(d.getId(), d.getDepartment().trim().toLowerCase());
        }

        if (!departmentsMap.containsValue(department)) throw new IllegalArgumentException("Could not find department called \"" + department + "\" in database");

        return departmentRepository.findByDepartmentIgnoreCase(department);
    }
}
