package com.sparta.tma.controllers;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.services.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RestController
@RequestMapping(path = "/manager")
public class ManagerController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/access")
    public String testingAccess() {

        return "Successful";
    }

    @GetMapping("/employees")
    public List<Employee> viewAllEmployeesWithinDepartment(Authentication authentication) {
        logger.info("inside view all employees GET method");

        Optional<AppUser> user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername());

        List<Employee> employeeList = employeeRepository.findAllByDepartment(user.get().getEmployee().getDepartment());

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.get().getEmployee());
        }

        if (employeeList.size() < 1) {
            logger.warn("No employees found");
        } else {
            logger.info("employee list size: {}", employeeList.size());
        }

        return (!employeeList.isEmpty() ? employeeList : Collections.emptyList());
    }

}
