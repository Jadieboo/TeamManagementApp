package com.sparta.tma;

import com.sparta.tma.daos.DepartmentDAO;
import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DepartmentTests {

    Logger logger = LoggerFactory.getLogger(getClass());
    TestUtils utils = new TestUtils();
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

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
        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(utils.employeeDetails());

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
            " H R , h r"
    })
    public void departmentDAOMethod_IllegalArgumentInput_ReturnsIllegalArgumentExceptionMessage(String fieldInput, String lowercaseText) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setDepartment(fieldInput);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new DepartmentDAO(departmentRepository).getDepartment(employeeDetails));

        String expected = "Could not find department called \"" + lowercaseText + "\" in database";

        assertEquals(expected, exception.getMessage());
    }
}
