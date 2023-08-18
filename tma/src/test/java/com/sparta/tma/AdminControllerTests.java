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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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
    @DisplayName("Create a list of all employee's in the database")
    public void viewAllEmployees() {

        List<Employee> employeeList = employeeRepository.findAll();

        assertTrue(employeeList.size() > 0);
    }

    @Test
    @DisplayName("Creates a new employee using createNewEmployee() DAO method to set id, first name, last name, role, department and project")
    public void newEmployeeDaoMethod() {

        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        String result = employee.toString();

        String expected = "Employee{id: 0, firstName: Unit, lastName: Test, role: ADMIN, department: Development, project: Web App}";

        assertEquals(expected, result);
    }

    // TODO: add params to unit test or create specific tests, to test null value and values that do not exist for
    //  BOTH department and project
    //  THEN create test branch and commit to github repo
    @ParameterizedTest
    @DisplayName("method will handle if the input is uppercase, lowercase, mixed case or has any whitespaces")
    @CsvSource({
            "HR, HR",
            "finance, Finance",
            "marKetINg, Marketing",
            "Sales, Sales",
            "design, Design",
            " development, Development",
            " customer Service , Customer Service"
    })
    public void setOrUpdateEmployeeDepartment(String department, String expectedDepartment) {
        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        logger.info("Employee information before update: {}", employee);

        EmployeeDTO updatedDepartment = new EmployeeDTO();
        updatedDepartment.setDepartment(department);

        employee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(updatedDepartment));

        logger.info("Employee information after update: {}", employee);

        String result = employee.getDepartment().toString();

        assertEquals(expectedDepartment, result);
    }

    @ParameterizedTest
    @DisplayName("method will handle if the input is uppercase, lowercase, mixed case or has any whitespaces")
    @CsvSource({
            "UNASSIGNED, Unassigned",
            "new starters, New Starters",
            "Attendance, Attendance",
            "paYroLl, Payroll",
            "accOUNTs, Accounts",
            " Web App Frontend, Web App Frontend",
            "Logo , Logo"
    })
    public void setOrUpdateEmployeeProject() {
        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        logger.info("Employee information before update: {}", employee);

        EmployeeDTO updateProject = new EmployeeDTO();
        updateProject.setProject("Logo");

        employee.setProject(new ProjectDAO(projectRepository).getProject(updateProject));

        logger.info("Employee information after update: {}", employee);

        String expected = "Logo";
        String result = employee.getProject().toString();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Testing getRoleDao method from RoleDAO to set or update role")
    public void setRoleDaoMethod() {

        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        EmployeeDTO setNewRole = new EmployeeDTO();
        setNewRole.setRole("manager");

        employee.setRole(new RoleDAO().getRole(setNewRole));

        String result = employee.toString();

        String expected = "Employee{id: 0, firstName: Unit, lastName: Test, role: MANAGER, department: Development, project: Web App}";

        assertEquals(expected, result);

    }


}
