package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;

public class EmployeeDAO {

    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;


    public EmployeeDAO(DepartmentRepository departmentRepository, ProjectRepository projectRepository) {
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
    }
    public Employee createNewEmployee(EmployeeDTO employeeDetails) {

        Employee newEmployee = new Employee();
        newEmployee.setId(0);
        newEmployee.setFirstName(employeeDetails.getFirstName());
        newEmployee.setLastName(employeeDetails.getLastName());
        newEmployee.setRole(new RoleDAO().getRole(employeeDetails));

        // TODO: does this violate single responsibility?
        newEmployee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(employeeDetails));
        newEmployee.setProject(new ProjectDAO(projectRepository).getProject(employeeDetails));

        return newEmployee;
    }
}
