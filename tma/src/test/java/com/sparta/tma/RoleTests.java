package com.sparta.tma;

import com.sparta.tma.DAOs.EmployeeDAO;
import com.sparta.tma.DAOs.RoleDAO;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RoleTests {

    TestUtils utils = new TestUtils();
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @ParameterizedTest
    @ValueSource(strings = {"admin", "MANAGER", " Employee "})
    @DisplayName("Given a string value of admin, manager or employee is passed, regardless of case or whitespacing, returns correct role")
    public void setOrUpdateRoleDaoMethod(String fieldInput) {

        String role = fieldInput.toUpperCase().trim();

        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(utils.employeeDetails());

        EmployeeDTO setNewRole = new EmployeeDTO();
        setNewRole.setRole(fieldInput);

        employee.setRole(new RoleDAO().getRole(setNewRole));

        String result = employee.toString();

        String expected = "Employee{id: 0, firstName: Unit, lastName: Test, role: " + role + ", department: Development, project: Web App}";

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Supervisor", "admine", " ", "", "!", "</>", "ABC", "123"})
    @DisplayName("RoleDAO.getRole() method handles if an illegal argument is passed, returns Illegal Argument Exception Message: Role not found, please enter a valid role (Admin/Manager/Employee)")
    public void roleDaoMethod_IllegalArgumentInput_ReturnsIllegalArgumentExceptionMessage(String fieldInput) {
        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(utils.employeeDetails());

        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setRole(fieldInput);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> employee.setRole(new RoleDAO().getRole(employeeDetails)));

        String expectedExceptionMessage = "Role not found, please enter a valid role (Admin/Manager/Employee)";

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Given a null element is passed to RoleDAO.getRole() method, returns NullPointerException message: Role not found: field is empty")
    public void roleDaoMethod_NullInput_ReturnsNullPointerExceptionMessage() {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setRole(null);

        Throwable exception = assertThrows(NullPointerException.class, () -> new RoleDAO().getRole(employeeDetails));

        String expectedExceptionMessage = "Role not found: field is empty";

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }
}
