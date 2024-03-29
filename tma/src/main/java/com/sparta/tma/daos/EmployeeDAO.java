package com.sparta.tma.daos;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service
public class EmployeeDAO {
    Logger logger = LoggerFactory.getLogger(getClass());
    protected DepartmentRepository departmentRepository;
    protected ProjectRepository projectRepository;
    public EmployeeDAO(DepartmentRepository departmentRepository, ProjectRepository projectRepository) {
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
    }
    public EmployeeDAO(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public Employee createNewEmployee(EmployeeDTO employeeDetails) {
        logger.info("create new employee method active inside EmployeeDAO");

        Employee newEmployee = new Employee();
        newEmployee.setId(0);
        newEmployee.setFirstName(getFormattedFirstName(employeeDetails));
        newEmployee.setLastName(getFormattedLastName(employeeDetails));
        newEmployee.setRole(new RoleDAO().getRole(employeeDetails));
        newEmployee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(employeeDetails));
        newEmployee.setProject(new ProjectDAO(projectRepository).getProject(employeeDetails));

        return newEmployee;
    }



    protected String getFormattedFirstName(EmployeeDTO employeeDetails) throws NullPointerException {

        if (employeeDetails.getFirstName() == null || employeeDetails.getFirstName().isBlank()) throw new NullPointerException("Error: First name field is null or blank");

        String name = employeeDetails.getFirstName().trim().toLowerCase();

        return name.toUpperCase().charAt(0) + name.substring(1);
    }

    protected String getFormattedLastName(EmployeeDTO employeeDetails) throws NullPointerException {

        if (employeeDetails.getLastName() == null || employeeDetails.getLastName().isBlank()) throw new NullPointerException("Error: Last name field is null or blank");

        String name = employeeDetails.getLastName().trim().toLowerCase();

        return name.toUpperCase().charAt(0) + name.substring(1);
    }


}
