package com.sparta.tma;

import com.sparta.tma.DAOs.DepartmentDAO;
import com.sparta.tma.DAOs.ProjectDAO;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.Entities.Employee;
import com.sparta.tma.Repositories.DepartmentRepository;
import com.sparta.tma.Repositories.EmployeeRepository;
import com.sparta.tma.Repositories.ProjectRepository;
import com.sparta.tma.Repositories.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AdminControllerTests {

    @Autowired
    private EmployeeRepository eRepo;
    @Autowired
    private RoleRepository rRepo;
    @Autowired
    private DepartmentRepository dRepo;
    @Autowired
    private ProjectRepository pRepo;

    @BeforeEach
    @AfterEach
    public void resetEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("MethodTesting");
        employeeDTO.setLastName("UnitTest");
        employeeDTO.setRole("employee");
        employeeDTO.setDepartment("Marketing");
        employeeDTO.setProject("Logo");

        Employee employee = eRepo.findEmployeeById(41);
        employee.setRole(rRepo.findRoleByRoleIgnoreCase(employeeDTO.getRole()));
        employee.setDepartment(dRepo.findByDepartmentIgnoreCase(employeeDTO.getDepartment()));
        employee.setProject(pRepo.findByProjectIgnoreCase(employeeDTO.getProject()));

        eRepo.save(employee);
    }

    @Test
    public void viewAllEmployees() {
        List<Employee> employeeList = eRepo.findAll();

        assertTrue(employeeList.size() > 0);
    }

    @Test
    public void  createNewEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(0);
        employeeDTO.setFirstName("TestingDto");
        employeeDTO.setLastName("UnitTest");
        employeeDTO.setRole("Admin");
        employeeDTO.setDepartment("HR");
        employeeDTO.setProject("unassigned");

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setRole(rRepo.findRoleByRoleIgnoreCase(employeeDTO.getRole()));
        employee.setDepartment(dRepo.findByDepartmentIgnoreCase(employeeDTO.getDepartment()));
        employee.setProject(pRepo.findByProjectIgnoreCase(employeeDTO.getProject()));

        Employee result = eRepo.save(employee);

        String expected = String.format("Employee{id: %s, firstName: %s, lastName: %s, role: Admin, department: HR, project: Unassigned}", result.getId(), employeeDTO.getFirstName(), employeeDTO.getLastName());

        assertEquals(expected, result.toString());
    }


    @Test
    public void updateEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartment("design");
        employeeDTO.setProject("logo");

        Employee original = eRepo.findEmployeeById(41);
        System.out.println("Original employee data: " + original.toString());

        Employee employee = eRepo.findEmployeeById(41);
        employee.setDepartment(new DepartmentDAO(dRepo).getDepartmentDao(employeeDTO));
        employee.setProject(pRepo.findById(1));

        Employee result = eRepo.save(employee);

        String expected = String.format("Employee{id: %s, firstName: %s, lastName: %s, role: %s, department: Design, project: Unassigned}",
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole()
        );

        System.out.println("Employee data after update: " + employee);

        assertEquals(expected, result.toString());

    }
}
