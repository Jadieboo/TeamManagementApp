package com.sparta.tma;

import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ManagerControllerTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
    @DisplayName("Given a department, create a list of all employee's assigned to that department")
    public void viewAllEmployeesWithinDepartment(int deptId) {

        Department department = departmentRepository.findById(deptId);

        List<Employee> employeeList = employeeRepository.findAllByDepartment(department);

        System.out.print(employeeList.toString());

        assertTrue(employeeList.size() > 0);
    }

}
