package com.sparta.tma.controllers;

import com.sparta.tma.DAOs.*;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.exceptions.EmployeeNotFoundException;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("employees")
    public List<Employee> viewAllEmployees() {

        List<Employee> employeeList = employeeRepository.findAll();

        if (employeeList.size() < 1) {
            logger.error("Employee list is empty, should be a minimum of one", employeeList);
        }

        return (!employeeList.isEmpty() ? employeeList : Collections.emptyList());
    }

    @Transactional
    @PostMapping("admin/register/employees")
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDetails) {

        Employee newEmployee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails);

        Employee employee = employeeRepository.saveAndFlush(newEmployee);
        logger.info("New employee saved, {}", employee);

        logger.info("New user is being created");
        AppUser newAppUser = new AppUserDAO(encoder, employeeRepository).createNewAppUser(employeeDetails, employee.getId());

        AppUser appUser = appUserRepository.saveAndFlush(newAppUser);
        logger.info("New user saved, {}", appUser);

        return employeeRepository.findById(employee.getId()).orElseThrow(() -> new EmployeeNotFoundException("Employee " + employee + " not found/created"));

    }

    @Transactional
    @PatchMapping("employees/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody EmployeeDTO jsonBody) {
        Employee updateEmployee = employeeRepository.findEmployeeById(id);
        logger.info("Changes being made to " + updateEmployee);

        updateEmployee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(jsonBody));
        updateEmployee.setProject(projectRepository.findById(1));

        logger.info("Employee department and project was updated: {}", updateEmployee);

        return employeeRepository.save(updateEmployee);
    }

}
