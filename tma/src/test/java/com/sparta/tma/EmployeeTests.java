package com.sparta.tma;

import com.sparta.tma.DAOs.EmployeeDAO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
