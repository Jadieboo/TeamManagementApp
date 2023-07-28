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
import com.sparta.tma.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateOrUpdateDaoMethodTests {
    @Autowired
    private EmployeeRepository eRepo;
    @Autowired
    private RoleRepository rRepo;
    @Autowired
    private DepartmentRepository dRepo;
    @Autowired
    private ProjectRepository pRepo;

    @BeforeEach
    public void resetEmployee() {
        int count = 0;
        count++;
        System.out.printf("Test: %s is running", count);

        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setFirstName("MethodTesting");
        empDto.setLastName("UnitTest");
        empDto.setRole("employee");
        empDto.setDepartment("Marketing");
        empDto.setProject("unassigned");

        Employee employee = eRepo.findEmployeeById(41);
        employee.setRole(rRepo.findRoleByRoleIgnoreCase(empDto.getRole()));
        employee.setDepartment(dRepo.findByDepartmentIgnoreCase(empDto.getDepartment()));
        employee.setProject(pRepo.findByProjectIgnoreCase(empDto.getProject()));

        eRepo.save(employee);

    }

    @Test
    @DisplayName("Creating a new employee using newEmployee() DAO method to set id, first and last name")
    public void newEmployeeDaoMethod() {
        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setId(0);
        empDto.setFirstName("newEmployeeDao");
        empDto.setLastName("UnitTest");
        empDto.setRole("Admin");
        empDto.setDepartment("HR");
        empDto.setProject("unassigned");

        Employee employee = new EmployeeDAO(eRepo).newEmployee(empDto);
        employee.setRole(rRepo.findRoleByRoleIgnoreCase(empDto.getRole()));
        employee.setDepartment(dRepo.findByDepartmentIgnoreCase(empDto.getDepartment()));
        employee.setProject(pRepo.findByProjectIgnoreCase(empDto.getProject()));

        Employee result = eRepo.save(employee);

        String expected = String.format("Employee{id: %s, firstName: %s, lastName: %s, role: Admin, department: HR, project: Unassigned}", result.getId(), empDto.getFirstName(), empDto.getLastName());

        assertEquals(expected, result.toString());

    }

    @Test
    @DisplayName("Testing getRoleDao method from RoleDAO to set or update role")
    public void setRoleDaoMethod() {
        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setRole("manager");

        Employee employee = eRepo.findEmployeeById(41);
        employee.setRole(new RoleDAO(rRepo).getRoleDao(empDto));

        Employee result = eRepo.save(employee);

        String expected = String.format("Employee{id: %s, firstName: %s, lastName: %s, role: Manager, department: %s, project: %s}",
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartment(),
                employee.getProject()
        );

        assertEquals(expected, result.toString());

    }

    @Test
    @DisplayName("Testing getDepartmentDao method from DepartmentDAO to set or update department")
    public void setDepartmentDaoMethod() {
        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setDepartment("Sales");

        Employee employee = eRepo.findEmployeeById(41);
        employee.setDepartment(new DepartmentDAO(dRepo).getDepartmentDao(empDto));

        Employee result = eRepo.save(employee);

        String expected = String.format("Employee{id: %s, firstName: %s, lastName: %s, role: %s, department: Sales, project: %s}",
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole(),
                employee.getProject()
        );

        assertEquals(expected, result.toString());
    }

    @Test
    @DisplayName("Testing getProjectDao method from ProjectDAO to set or update project")
    public void setProjectDaoMethod() {
        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setProject("products");

        Employee employee = eRepo.findEmployeeById(41);
        employee.setProject(new ProjectDAO(pRepo).getProjectDao(empDto));

        Employee result = eRepo.save(employee);

        String expected = String.format("Employee{id: %s, firstName: %s, lastName: %s, role: %s, department: %s, project: Products}",
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole(),
                employee.getDepartment()
        );

        assertEquals(expected, result.toString());
    }
}
