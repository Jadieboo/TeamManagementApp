package com.sparta.tma;

import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Department;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeTests {

    TestUtils utils = new TestUtils();
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Creates a new employee using createNewEmployee() DAO method to set id, first name, last name, role, department and project")
    public void newEmployeeDaoMethod() {

        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(utils.employeeDetails());

        String result = employee.toString();

        String expected = "Employee{id: 0, firstName: Unit, lastName: Test, role: ADMIN, department: Development, project: Web App}";

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Given first name field is null, returns Null Pointer Exception message: Error: First name field is null or blank")
    public void firstNameNullInput_ReturnsNullPointerExceptionMessage() {

        EmployeeDTO employeeDetails = utils.employeeDetails();
        employeeDetails.setFirstName(null);

        Throwable exception = assertThrows(NullPointerException.class, () -> new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails));

        String expectedExceptionMessage = "Error: First name field is null or blank";

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("Given first name field is empty or blank, returns Null Pointer Exception message: Error: First name field is null or blank")
    public void firstNameEmptyOrBlankInput_ReturnsNullPointerExceptionMessage() {

        EmployeeDTO employeeDetails = utils.employeeDetails();
        employeeDetails.setFirstName(null);

        Throwable exception = assertThrows(NullPointerException.class, () -> new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails));

        String expectedExceptionMessage = "Error: First name field is null or blank";

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Jade, Jade",
            "DANNY, Danny",
            "ollie, Ollie",
            "AleXaNdRiA, Alexandria"
    })
    @DisplayName("Given first name field is uppercase, lowercase, or mixed, returns first name formatted with a capitalised initial")
    public void formattedFirstName_CapitalisedInitial(String fieldInput, String formattedFirstName) {

        EmployeeDTO employeeDetails = utils.employeeDetails();
        employeeDetails.setFirstName(fieldInput);

        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails);

        assertEquals(employee.getFirstName(), formattedFirstName);
    }

    @Test
    @DisplayName("Given last name field is null, returns Null Pointer Exception message: Error: Last name field is null or blank")
    public void lastNameNullInput_ReturnsNullPointerExceptionMessage() {
        EmployeeDTO employeeDetails = utils.employeeDetails();
        employeeDetails.setLastName(null);

        Throwable exception = assertThrows(NullPointerException.class, () -> new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails));

        String expectedExceptionMessage = "Error: Last name field is null or blank";

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("Given last name field is empty or blank, returns Null Pointer Exception message: Error: Last name field is null or blank")
    public void lastNameEmptyOrBlankInput_ReturnsNullPointerExceptionMessage() {

        EmployeeDTO employeeDetails = utils.employeeDetails();
        employeeDetails.setLastName(null);

        Throwable exception = assertThrows(NullPointerException.class, () -> new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails));

        String expectedExceptionMessage = "Error: Last name field is null or blank";

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Sale, Sale",
            "KIRWAN, Kirwan",
            "queen, Queen",
            "WinCHesTeR, Winchester"
    })
    @DisplayName("Given last name field is uppercase, lowercase, or mixed, returns last name formatted with a capitalised initial")
    public void formattedLastName_CapitalisedInitial(String fieldInput, String formattedLastName) {

        EmployeeDTO employeeDetails = utils.employeeDetails();
        employeeDetails.setLastName(fieldInput);

        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails);

        assertEquals(employee.getLastName(), formattedLastName);
    }

    @Test
    @DisplayName("Return all employees where department = HR, project = Unassigned and role = Employee")
    public void employeeFindByCustomQuery() {
        Department department = departmentRepository.findById(1);
        Project project = projectRepository.findById(1);
        List<Employee> employeeList = employeeRepository.findAllByDepartmentAndProjectWithRoleEmployee(department, project);

        List<Employee> checkedList = new ArrayList<>();

        for (Employee e : employeeList) {
            if (
                    e.getRole().name().equals("EMPLOYEE") &&
                    e.getDepartment().toString().equals("HR") &&
                    e.getProject().toString().equals("Unassigned")
            ) {
                checkedList.add(e);
            }
        }

        employeeList.forEach(System.out::println);

        assertEquals(employeeList.size(), checkedList.size());
    }

}
