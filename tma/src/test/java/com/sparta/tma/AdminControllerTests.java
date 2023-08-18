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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AdminControllerTests {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

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
    public void viewAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();

        assertTrue(employeeList.size() > 0);
    }

    @Test
    public void  createNewEmployee() {
        Employee employee = new EmployeeDAO(employeeRepository, departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        String expected = "Employee{id: 0, firstName: Unit, lastName: Test, role: ADMIN, department: Development, project: Web App}";

        String result = employee.toString();

        assertEquals(expected, result);
    }

    // TODO: add params to unit test or create specific tests, to test null value and values that do not exist for
    //  BOTH department and project
    //  THEN create test branch and commit to github repo
    @Test
    public void updateEmployeeDepartment() {
        Employee employee = new EmployeeDAO(employeeRepository, departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        logger.info("Employee information before update: {}", employee);

        EmployeeDTO updateDepartment = new EmployeeDTO();
        updateDepartment.setDepartment("design");

        employee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(updateDepartment));

        logger.info("Employee information after update: {}", employee);

        String expected = "Design";
        String result = employee.getDepartment().toString();

        assertEquals(expected, result);
    }

    @Test
    public void updateEmployeeProject() {
        Employee employee = new EmployeeDAO(employeeRepository, departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        logger.info("Employee information before update: {}", employee);

        EmployeeDTO updateProject = new EmployeeDTO();
        updateProject.setProject("Logo");

        employee.setProject(new ProjectDAO(projectRepository).getProject(updateProject));

        logger.info("Employee information after update: {}", employee);

        String expected = "Logo";
        String result = employee.getProject().toString();

        assertEquals(expected, result);
    }


}
