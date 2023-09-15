package com.sparta.tma.controllers;

import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class EmployeeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("employee/access")
    public String testEmployeeAccess() {
        return "Successful";
    }

    @GetMapping("employee/employees")
    public List<Employee> viewAllEmployees() {

        List<Employee> employeeList = employeeRepository.findAll();

        if (employeeList.size() < 1) {
            logger.error("Employee list is empty, should be a minimum of one", employeeList);
        }

        return (!employeeList.isEmpty() ? employeeList : Collections.emptyList());
    }
}
