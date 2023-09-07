package com.sparta.tma;

import com.sparta.tma.DAOs.EmployeeDAO;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EmployeeTests {

    TestUtils utils = new TestUtils();
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

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

}