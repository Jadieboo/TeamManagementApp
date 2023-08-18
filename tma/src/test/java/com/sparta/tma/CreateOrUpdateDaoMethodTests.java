package com.sparta.tma;

import com.sparta.tma.DAOs.DepartmentDAO;
import com.sparta.tma.DAOs.EmployeeDAO;
import com.sparta.tma.DAOs.ProjectDAO;
import com.sparta.tma.DAOs.RoleDAO;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.relation.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateOrUpdateDaoMethodTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    public EmployeeDTO employeeDetails() {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setFirstName("Unit");
        employeeDetails.setLastName("Test");
        employeeDetails.setRole("Admin");
        employeeDetails.setDepartment("Development");
        employeeDetails.setProject("Web App");

        return employeeDetails;
    }

    @Test
    @DisplayName("Creating a new employee using createNewEmployee() DAO method to set id, first name, last name, role, department and project")
    public void newEmployeeDaoMethod() {

        Employee employee = new EmployeeDAO(employeeRepository, departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        String result = employee.toString();

        String expected = "Employee{id: 0, firstName: Unit, lastName: Test, role: ADMIN, department: Development, project: Web App}";

        assertEquals(expected, result.toString());
    }

    @Test
    @DisplayName("Testing getRoleDao method from RoleDAO to set or update role")
    public void setRoleDaoMethod() {

        Employee employee = new EmployeeDAO(employeeRepository, departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        EmployeeDTO setNewRole = new EmployeeDTO();
        setNewRole.setRole("manager");

        employee.setRole(new RoleDAO().getRole(setNewRole));

        String result = employee.toString();

        String expected = "Employee{id: 0, firstName: Unit, lastName: Test, role: MANAGER, department: Development, project: Web App}";

        assertEquals(expected, result.toString());

    }

    @Test
    @DisplayName("Testing getDepartmentDao method from DepartmentDAO to set or update department")
    public void setDepartmentDaoMethod() {

        Employee employee = new EmployeeDAO(employeeRepository, departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        EmployeeDTO setNewDepartment = new EmployeeDTO();
        setNewDepartment.setDepartment("Sales");

        employee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(setNewDepartment));

        Employee result = employeeRepository.save(employee);

        String expected = String.format("Employee{id: %s, firstName: %s, lastName: %s, role: %s, department: Sales, project: %s}",
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole(),
                employee.getProject()
        );

        assertEquals(expected, result.toString());
    }

    @Test
    @DisplayName("Testing getProjectDao method from ProjectDAO to set or update project")
    public void setProjectDaoMethod() {
        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setProject("products");

        Employee employee = employeeRepository.findEmployeeById(41);
        employee.setProject(new ProjectDAO(projectRepository).getProject(empDto));

        Employee result = employeeRepository.save(employee);

        String expected = String.format("Employee{id: %s, firstName: %s, lastName: %s, role: %s, department: %s, project: Products}",
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole(),
                employee.getDepartment()
        );

        assertEquals(expected, result.toString());
    }
}
