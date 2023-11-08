package com.sparta.tma;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.services.ViewEmployeesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        List<Employee> results = viewEmployeesService.getAllEmployees(user);

        assertEquals(expected.size()-1, results.size());
    }
}
