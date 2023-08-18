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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.relation.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateOrUpdateDaoMethodTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    public EmployeeDTO employeeDetails() {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setFirstName("Unit");
        employeeDetails.setLastName("Test");
        employeeDetails.setRole("Admin");
        employeeDetails.setDepartment("Development");
        employeeDetails.setProject("Web App");

        return employeeDetails;
    }






}
