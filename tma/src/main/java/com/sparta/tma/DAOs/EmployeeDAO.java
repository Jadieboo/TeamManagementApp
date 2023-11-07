package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.ProjectRepository;

//@Service
public class EmployeeDAO {

//    @Autowired
    private DepartmentRepository departmentRepository;
//    @Autowired
    private ProjectRepository projectRepository;

    public EmployeeDAO(DepartmentRepository departmentRepository, ProjectRepository projectRepository) {
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
    }

    public Employee createNewEmployee(EmployeeDTO employeeDetails) {

        Employee newEmployee = new Employee();
        newEmployee.setId(0);
        newEmployee.setFirstName(getFormattedFirstName(employeeDetails));
        newEmployee.setLastName(getFormattedLastName(employeeDetails));
        newEmployee.setRole(new RoleDAO().getRole(employeeDetails));
        newEmployee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(employeeDetails));
        newEmployee.setProject(new ProjectDAO(projectRepository).getProject(employeeDetails));

        return newEmployee;
    }

    private String getFormattedFirstName(EmployeeDTO employeeDetails) throws NullPointerException {

        if (employeeDetails.getFirstName() == null || employeeDetails.getFirstName().isBlank()) throw new NullPointerException("Error: First name field is null or blank");

        String name = employeeDetails.getFirstName().trim().toLowerCase();

        return name.toUpperCase().charAt(0) + name.substring(1);
    }

    private String getFormattedLastName(EmployeeDTO employeeDetails) throws NullPointerException {

        if (employeeDetails.getLastName() == null || employeeDetails.getLastName().isBlank()) throw new NullPointerException("Error: Last name field is null or blank");

        String name = employeeDetails.getLastName().trim().toLowerCase();

        return name.toUpperCase().charAt(0) + name.substring(1);
    }


}
