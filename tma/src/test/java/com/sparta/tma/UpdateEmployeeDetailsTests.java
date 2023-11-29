package com.sparta.tma;

import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("As a manager - Update employee's currently assigned project")
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

        Employee updatedEmployee = new EmployeeDAO(employeeRepository, projectRepository).updateAssignedProjectToEmployee(5, employeeDetails);

        String result = updatedEmployee.getProject().toString();

        assertThat(result, is(expected));
    }

    @Test
    @DisplayName("As a admin - Update employee's first name")
    public void updateEmployeeFirstName() {

    }
}
