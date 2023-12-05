package com.sparta.tma;

import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.daos.UpdateEmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Role;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UpdateEmployeeDetailsTests {
    TestUtils utils = new TestUtils();

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Test
    @DisplayName("Update employee's currently assigned project")
    public void updateAssignedProject() {
        Employee employee = employeeRepository.findEmployeeById(5);

        String currentProject = employee.getProject().toString();

        String expected = "Logo";

        if (currentProject.equals(expected)) {
            expected = "Web App";
        }

        EmployeeDTO employeeDetails = utils.setEmployeeDetails(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole().name(),
                employee.getDepartment().toString(),
                expected);

        Employee updatedEmployee = new UpdateEmployeeDAO(employeeRepository, projectRepository).updateAssignedProjectToEmployee(5, employeeDetails);

        String result = updatedEmployee.getProject().toString();

        assertThat(result, is(expected));
    }

    @Test
    @DisplayName("Update employee's first name")
    public void updateEmployeeFirstName() {
        // Arrange
        EmployeeDTO employeeDetails = new EmployeeDTO();
        int employeeIdToUpdate = 5;
        Employee originalEmployee = employeeRepository.findEmployeeById(employeeIdToUpdate);
        String originalFirstName = originalEmployee.getFirstName();
        String updateFirstName = "Oli";

        // Act
        employeeDetails.setId(employeeIdToUpdate);
        employeeDetails.setFirstName(updateFirstName);
        Employee updatedEmployee = new UpdateEmployeeDAO(employeeRepository, departmentRepository, projectRepository).updateEmployeeDetails(employeeDetails);

        // Assert
        assertAll("Verify employee update",
                () -> assertNotEquals(originalFirstName, updatedEmployee.getFirstName(), "First name should be updated"),
                () -> assertEquals(updateFirstName, updatedEmployee.getFirstName(), "First name should be 'Oli'")
        );
    }

    @Test
    @DisplayName("Update employee's last name")
    public void updateEmployeeLastName() {
        // Arrange
        EmployeeDTO employeeDetails = new EmployeeDTO();
        int employeeIdToUpdate = 5;
        Employee originalEmployee = employeeRepository.findEmployeeById(employeeIdToUpdate);
        String originalLastName = originalEmployee.getLastName();
        String updateLastName = "Sale";

        // Act
        employeeDetails.setId(employeeIdToUpdate);
        employeeDetails.setLastName(updateLastName);
        Employee updatedEmployee = new UpdateEmployeeDAO(employeeRepository, departmentRepository, projectRepository).updateEmployeeDetails(employeeDetails);

        // Assert
        assertAll("Verify employee update",
                () -> assertNotEquals(originalLastName, updatedEmployee.getLastName(), "Last name should be updated"),
                () -> assertEquals(updateLastName, updatedEmployee.getLastName(), "Last name should be 'Sale'")
        );
    }

    @Test
    @DisplayName("Update employee's role")
    public void updateEmployeeRole() {
        // Arrange
        EmployeeDTO employeeDetails = new EmployeeDTO();
        int employeeIdToUpdate = 5;
        Employee originalEmployee = employeeRepository.findEmployeeById(employeeIdToUpdate);
        String originalRole = originalEmployee.getRole().name();
        String updateRole = "Manager";

        // Act
        employeeDetails.setId(employeeIdToUpdate);
        employeeDetails.setRole(updateRole);
        Employee updatedEmployee = new UpdateEmployeeDAO(employeeRepository, departmentRepository, projectRepository).updateEmployeeDetails(employeeDetails);

        // Assert
        assertAll("Verify employee update",
                () -> assertNotEquals(originalRole, updatedEmployee.getRole().name(), "Role should be updated"),
                () -> assertEquals(updateRole.toUpperCase(), updatedEmployee.getRole().name(), "Role should be 'Manager'")
        );
    }

    @Test
    @DisplayName("Update employee's department")
    public void updateEmployeeDepartment() {
        // Arrange
        EmployeeDTO employeeDetails = new EmployeeDTO();
        int employeeIdToUpdate = 5;
        Employee originalEmployee = employeeRepository.findEmployeeById(employeeIdToUpdate);
        String originalDepartment = originalEmployee.getDepartment().getDepartment();
        String updateDepartment = "Design";

        // Act
        employeeDetails.setId(employeeIdToUpdate);
        employeeDetails.setDepartment(updateDepartment);
        Employee updatedEmployee = new UpdateEmployeeDAO(employeeRepository, departmentRepository, projectRepository).updateEmployeeDetails(employeeDetails);

        // Assert
        assertAll("Verify employee update",
                () -> assertNotEquals(originalDepartment, updatedEmployee.getDepartment().getDepartment(), "Department should be updated"),
                () -> assertEquals(updateDepartment, updatedEmployee.getDepartment().getDepartment(), "Department should be 'Design'")
        );
    }

    @Test
    @DisplayName("Update employee's project")
    public void updateEmployeeProject() {
        // Arrange
        EmployeeDTO employeeDetails = new EmployeeDTO();
        int employeeIdToUpdate = 5;
        Employee originalEmployee = employeeRepository.findEmployeeById(employeeIdToUpdate);
        String originalProject = originalEmployee.getProject().getProject();
        String updateProject = "Logo";

        // Act
        employeeDetails.setId(employeeIdToUpdate);
        employeeDetails.setProject(updateProject);
        Employee updatedEmployee = new UpdateEmployeeDAO(employeeRepository, departmentRepository, projectRepository).updateEmployeeDetails(employeeDetails);

        // Assert
        assertAll("Verify employee update",
                () -> assertNotEquals(originalProject, updatedEmployee.getProject().getProject(), "Project should be updated"),
                () -> assertEquals(updateProject, updatedEmployee.getProject().getProject(), "Project should be 'Logo'")
        );
    }

    @Test
    @DisplayName("Check employee matches form data")
    public void updateEmployee() {
        EmployeeDTO formData = new EmployeeDTO();
        Employee expected = new Employee();
        int employeeIdToUpdate = 7;
        Employee originalEmployee = employeeRepository.findEmployeeById(employeeIdToUpdate);

        formData.setId(employeeIdToUpdate);
        formData.setFirstName("Trev");
        formData.setLastName("Dun");
        formData.setRole("manager");
        formData.setProject("Logo");

        expected.setId(employeeIdToUpdate);
        expected.setFirstName("Trev");
        expected.setLastName("Dun");
        expected.setRole(Role.MANAGER);
        expected.setDepartment(departmentRepository.findByDepartmentIgnoreCase("HR"));
        expected.setProject(projectRepository.findByProjectIgnoreCase("Logo"));
        Employee updatedEmployee = new UpdateEmployeeDAO(employeeRepository, departmentRepository, projectRepository).updateEmployeeDetails(formData);

        assertEquals(expected, updatedEmployee);
    }
}
