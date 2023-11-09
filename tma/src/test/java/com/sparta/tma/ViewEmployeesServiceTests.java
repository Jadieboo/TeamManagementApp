package com.sparta.tma;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.services.ViewEmployeesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ViewEmployeesServiceTests {
    @Autowired
    private ViewEmployeesService viewEmployeesService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @DisplayName("returns all employees")
    public void getAllEmployees() {
        AppUser user = appUserRepository.findByUsername("admin").get();

        List<Employee> expected = employeeRepository.findAll();
        List<Employee> results = viewEmployeesService.getAllEmployees();

        assertEquals(expected.size(), results.size());
    }

    @Test
    @DisplayName("Given a department, returns all employees within that department")
    public void getEmployeesByDepartment() {
        AppUser user = appUserRepository.findByUsername("manager").get();
        Department department = user.getEmployee().getDepartment();

        List<Employee> employees = employeeRepository.findAll();
        List<Employee> results = viewEmployeesService.getEmployeesByDepartment(department);
        List<Employee> expected = new ArrayList<>();

        for (Employee e : employees) {
            if (e.getDepartment().equals(department)) {
                expected.add(e);
            }
        }


        assertEquals(expected.size(), results.size());
    }
}
