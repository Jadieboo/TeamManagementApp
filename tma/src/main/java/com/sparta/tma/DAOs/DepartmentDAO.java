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

    public Department getDepartment(EmployeeDTO employeeDetails) throws NullPointerException, IllegalArgumentException {
        // TODO: handle if employeeDetails.getDepartment() is empty

        if (employeeDetails.getDepartment() == null || employeeDetails.getDepartment().isBlank()) throw new NullPointerException("Error: Department field is null or blank");

        String departmentField = employeeDetails.getDepartment().trim().toLowerCase();

        HashMap<Integer, String> departmentsMap = new HashMap<>();

        for ( Department d : departmentRepository.findAll()) {
            departmentsMap.put(d.getId(), d.getDepartment().trim().toLowerCase());
        }

        if (!departmentsMap.containsValue(departmentField)) throw new IllegalArgumentException("Could not find department called \"" + departmentField + "\" in database");

        return departmentRepository.findByDepartmentIgnoreCase(departmentField);
    }
}
