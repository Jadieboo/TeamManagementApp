package com.sparta.tma;

import com.sparta.tma.DAOs.DepartmentDAO;
import com.sparta.tma.DAOs.EmployeeDAO;
import com.sparta.tma.DAOs.ProjectDAO;
import com.sparta.tma.DAOs.RoleDAO;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Project;
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

import static org.junit.jupiter.api.Assertions.*;

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
        EmployeeDTO testEmployeeDetails = new EmployeeDTO();
        testEmployeeDetails.setFirstName("Unit");
        testEmployeeDetails.setLastName("Test");
        testEmployeeDetails.setRole("Admin");
        testEmployeeDetails.setDepartment("Development");
        testEmployeeDetails.setProject("Web App");

        return testEmployeeDetails;
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
    @ParameterizedTest
    @DisplayName("method returns a department regardless of the input being uppercase, lowercase, mixed case or has any whitespaces")
    @CsvSource({
            "HR, HR",
            "finance, Finance",
            "marKetINg, Marketing",
            "Sales, Sales",
            "design , Design",
            " development, Development",
            " customer Service , Customer Service"
    })
    public void setOrUpdateEmployeeDepartment(String department, String expectedDepartment) {
        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        logger.info("Unit Test employee information before update: {}", employee);

        EmployeeDTO updatedDepartment = new EmployeeDTO();
        updatedDepartment.setDepartment(department);

        employee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(updatedDepartment));

        logger.info("Unit Test employee information after update: {}", employee);

        String result = employee.getDepartment().toString();

        assertEquals(expectedDepartment, result);
    }

    @Test
    @DisplayName("DepartmentDAO.getDepartment() method handles if a null is passed, returns a NullPointerException message \"Error: Department field is null or blank\"")
    public void departmentDAOMethod_NullInput_ReturnsNullPointerExceptionMessage() {

        EmployeeDTO emptyEmployeeDetails = new EmployeeDTO();

        Throwable exception = assertThrows(NullPointerException.class, () -> new DepartmentDAO(departmentRepository).getDepartment(emptyEmployeeDetails));

        String expected = "Error: Department field is null or blank";

        assertEquals(expected, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("DepartmentDAO.getDepartment() method handles if a empty/blank string is passed, returns a NullPointerException message \"Error: Department field is null or blank\"")
    public void departmentDAOMethod_BlankInput_ReturnsNullPointerExceptionMessage(String departmentField) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setDepartment(departmentField);

        Throwable exception = assertThrows(NullPointerException.class, () -> new DepartmentDAO(departmentRepository).getDepartment(employeeDetails));

        String expected = "Error: Department field is null or blank";

        assertEquals(expected, exception.getMessage());

    }

    @ParameterizedTest
    @DisplayName("DepartmentDAO.getDepartment() method handles if an illegal argument is passed, returns an IllegalArgumentException message \"Could not find department called \"field input\" in database\"")
    @CsvSource({
            "Random Text, random text",
            "!, !",
            "123, 123",
            "., .",
            "<, <",
            "acb123, acb123",
            "Financee, financee",
            " H R , h r",
            " , ",
            ","
    })
    public void departmentDAOMethod_IllegalArgumentInput_ReturnsIllegalArgumentExceptionMessage(String fieldInput, String lowercaseText) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setDepartment(fieldInput);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new DepartmentDAO(departmentRepository).getDepartment(employeeDetails));

        String expected = "Could not find department called \"" + lowercaseText + "\" in database";

        assertEquals(expected, exception.getMessage());

    }

    @ParameterizedTest
    @DisplayName("ProjectDAO.getProject() method returns a project regardless of the input being uppercase, lowercase, mixed case or has any whitespaces")
    @CsvSource({
            "Unassigned, Unassigned",
            "new starters, New Starters",
            "LOGO, Logo",
            "aTtEndANCE, Attendance",
            " Web App Frontend, Web App Frontend",
            "web app backend , Web App Backend",
            " Accounts , Accounts"
    })
    public void setOrUpdateEmployeeProject(String project, String expectedProject) {
        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        logger.info("Unit Test employee information before update: {}", employee);

        EmployeeDTO updatedProject = new EmployeeDTO();
        updatedProject.setProject(project);

        employee.setProject(new ProjectDAO(projectRepository).getProject(updatedProject));

        logger.info("Unit Test employee information after update: {}", employee);

        String result = employee.getProject().toString();

        assertEquals(expectedProject, result);
    }

    @ParameterizedTest
    @CsvSource({
            "un-assigned, un-assigned",
            "projekt, projekt",
            "New  Starters, new  starters",
            "Random Text, random text",
            "!, !",
            "123, 123",
            "., .",
            "<, <",
            "acb123, acb123"
    })
    @DisplayName("ProjectDAO.getProject() method handles if an illegal argument is passed, returns an IllegalArgumentException message: Could not find project called \"field input\" in database")
    public void ProjectDAOMethod_IllegalArgumentInput_ReturnsIllegalArgumentExceptionMessage(String fieldInput, String lowercaseText) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setProject(fieldInput);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new ProjectDAO(projectRepository).getProject(employeeDetails));

        String expected = "Could not find project called \"" + lowercaseText + "\" in database";

        assertEquals(expected, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("ProjectDAO.getProject() method handles if an empty/blank string is passed, returns default project: Unassigned")
    public void projectDAOMethod_BlankInput_ReturnsDefaultProject_Unassigned(String projectField) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setProject(projectField);

        Project result = new ProjectDAO(projectRepository).getProject(employeeDetails);

        Project expected = projectRepository.findById(1);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("ProjectDAO.getProject() method handles if a null is passed, returns default project: Unassigned")
    public void projectDAOMethod_NullInput_ReturnsDefaultProject_Unassigned() {
        EmployeeDTO emptyEmployeeDTO = new EmployeeDTO();

        Project result = new ProjectDAO(projectRepository).getProject(emptyEmployeeDTO);

        Project expected = projectRepository.findById(1);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Testing getRoleDao method from RoleDAO to set or update role")
    public void setOrUpdateRoleDaoMethod() {

        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        EmployeeDTO setNewRole = new EmployeeDTO();
        setNewRole.setRole("manager");

        employee.setRole(new RoleDAO().getRole(setNewRole));

        String result = employee.toString();

        String expected = "Employee{id: 0, firstName: Unit, lastName: Test, role: MANAGER, department: Development, project: Web App}";

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Supervisor", "admine", " ", "", "!", "</>", "ABC", "123"})
    @DisplayName("RoleDAO.getRole() method handles if a null is passed, returns Illegal Argument Exception Message: Role not found")
    public void roleDaoMethod_IllegalArgumentInput_ReturnsIllegalArgumentExceptionMessage(String fieldInput) {
        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails());

        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setRole(fieldInput);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> employee.setRole(new RoleDAO().getRole(employeeDetails)));

        String expectedExceptionMessage = "Role not found";

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    //TODO: test RoleDAO for null and change initial setRole test to a parameterized test


}
