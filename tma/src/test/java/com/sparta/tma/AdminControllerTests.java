package com.sparta.tma;

import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminControllerTests {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    @DisplayName("Create a list of all employee's in the database")
    public void viewAllEmployees() {

        List<Employee> employeeList = employeeRepository.findAll();

        assertTrue(employeeList.size() > 0);
    }


}
