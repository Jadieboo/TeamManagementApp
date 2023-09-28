package com.sparta.tma.controllers;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Role;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/access")
    public String testEmployeeAccess() {
        return "Successful";
    }


    // all colleagues with any role
    @GetMapping("/colleagues")
    public List<Employee> viewAllColleaguesWithinDepartment(Authentication authentication) {

        logger.info("inside view all colleagues within department GET method");

        String username = authentication.getName();

        logger.info("authentication: {}, " +
                "Username: {}", authentication, username);

        Optional<AppUser> user = appUserRepository.findByUsername(username);

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

    // all colleagues with role employee, no manager or admins
    @GetMapping("/colleagues/project")
    public List<Employee> viewAllColleaguesWithinProject(Authentication authentication) {

        logger.info("inside view all colleagues within project GET method");
        
        String username = authentication.getName();

        logger.info("authentication: {}, " +
                "Username: {}", authentication, username);

        Optional<AppUser> user = appUserRepository.findByUsername(username);

        List<Employee> employeeList = employeeRepository.findAllByDepartmentAndProjectWithRoleEmployee(user.get().getEmployee().getDepartment(), user.get().getEmployee().getProject());

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
