package com.sparta.tma;

import com.sparta.tma.DAOs.EmployeeDAO;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.Entities.Employee;
import com.sparta.tma.Repositories.DepartmentRepository;
import com.sparta.tma.Repositories.EmployeeRepository;
import com.sparta.tma.Repositories.ProjectRepository;
import com.sparta.tma.Repositories.RoleRepository;
import lombok.experimental.StandardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeControllerTests {

    @Autowired
    private EmployeeRepository eRepo;
    @Autowired
    private RoleRepository rRepo;
    @Autowired
    private DepartmentRepository dRepo;
    @Autowired
    private ProjectRepository pRepo;

    @Test
    public void  createNewEmployee() {
        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setId(0);
        empDto.setFirstName("TestingDto");
        empDto.setLastName("Test");
        empDto.setRole("Admin");
        empDto.setDepartment("HR");
        empDto.setProject("unassigned");

        Employee employee = new Employee();
        employee.setId(empDto.getId());
        employee.setFirstName(empDto.getFirstName());
        employee.setLastName(empDto.getLastName());
        employee.setRole(rRepo.findRoleByRoleIgnoreCase(empDto.getRole()));
        employee.setDepartment(dRepo.findByDepartmentIgnoreCase(empDto.getDepartment()));
        employee.setProject(pRepo.findByProjectIgnoreCase(empDto.getProject()));

        eRepo.save(employee);

        Optional<Employee> result = eRepo.findById(employee.getId());
        if (result.isEmpty()) Assertions.fail("Employee object is empty");
    }

    @Test
    public void updateEmployee() {
        String expected = "Employee{id: 25, firstName: TestingDto, lastName: Test, role: Admin, department: Sales, project: Unassigned}";

        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setId(25);
        empDto.setDepartment("Sales");
        empDto.setProject("unassigned");

        Employee original = eRepo.findEmployeeById(25);
        System.out.println("Original employee data: " + original.toString());

        Employee employee = eRepo.findById(empDto.getId()).get();
        employee.setDepartment(dRepo.findByDepartmentIgnoreCase(empDto.getDepartment()));
        employee.setProject(pRepo.findByProjectIgnoreCase(empDto.getProject()));

        Employee result = eRepo.save(employee);

        System.out.println("Employee data after update: " + employee);

        assertEquals(expected, result.toString());

    }
}
