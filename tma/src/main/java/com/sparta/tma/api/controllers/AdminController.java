package com.sparta.tma.api.controllers;

import com.sparta.tma.daos.*;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.dtos.EmployeesJSON;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.exceptions.EmployeeNotFoundException;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import com.sparta.tma.services.UserAccountService;
import com.sparta.tma.services.ViewEmployeesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


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
    UserAccountService userAccountService;
    @Autowired
    ViewEmployeesService viewEmployeesService;


    @GetMapping("/admin/employees")
    public List<Employee> viewAllEmployees(Authentication authentication) {
        logger.info("view all employees method active");

        AppUser user = appUserRepository.findByUsername(authentication.getName()).get();

        List<Employee> employeeList = viewEmployeesService.getAllEmployees();

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        return employeeList;
    }

    @Transactional
    @PostMapping("/admin/register/employee")
    public Employee createSingleEmployeeAPI(@RequestBody EmployeeDTO employeeDetails) {

        Employee newEmployee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails);

        Employee employee = employeeRepository.saveAndFlush(newEmployee);
        logger.info("New employee saved, {}", employee);

        logger.info("New user is being created");
        AppUser newAppUser = userAccountService.createNewAppUser(employeeDetails, employee.getId());

        AppUser appUser = appUserRepository.saveAndFlush(newAppUser);
        logger.info("New user saved, {}", appUser);

        return employeeRepository.findById(employee.getId()).orElseThrow(() -> new EmployeeNotFoundException("Employee " + employee + " not found/created"));
    }

    @Transactional
    @PostMapping("/admin/register/employees")
    public ResponseEntity<String> createMultipleEmployeesAPI(@RequestBody EmployeesJSON employeesJSON) {
        logger.info("creating multiple employees from JSON data");
        List<Employee> addedEmployeesList = new ArrayList<>();

        for (EmployeeDTO employeeDetails : employeesJSON.getEmployeeList()) {
            Employee newEmployee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails);

            Employee employee = employeeRepository.saveAndFlush(newEmployee);
            logger.info("New employee saved, {}", employee);
            addedEmployeesList.add(employee);

            logger.info("New user is being created");
            AppUser newAppUser = userAccountService.createNewAppUser(employeeDetails, employee.getId());

            AppUser appUser = appUserRepository.saveAndFlush(newAppUser);
            logger.info("New user saved, {}", appUser);
        }

        return new ResponseEntity<>("{\"message\":\" Employees successfully added to the database\" \n \"newEmployees\": " + addedEmployeesList +" }", HttpStatus.OK);

    }

    @Transactional
    @PatchMapping("/admin/employees/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody EmployeeDTO employeeDetails) {
        Employee updateEmployee = employeeRepository.findEmployeeById(id);
        logger.info("Changes being made to " + updateEmployee);

        if (!Objects.equals(updateEmployee.getFirstName(), employeeDetails.getFirstName()) || !Objects.equals(updateEmployee.getLastName(), employeeDetails.getLastName())) {
            updateEmployee.setFirstName(employeeDetails.getFirstName());
            updateEmployee.setLastName(employeeDetails.getLastName());

            AppUser updateUser = appUserRepository.findByEmployeeId(updateEmployee.getId());
            updateUser.setUsername(userAccountService.createUserUsername(employeeDetails, updateEmployee.getId()));
            appUserRepository.saveAndFlush(updateUser);
        }

        if (!updateEmployee.getRole().toString().equals(employeeDetails.getRole())) {
            updateEmployee.setRole(new RoleDAO().getRole(employeeDetails));

            AppUser updateUser = appUserRepository.findByEmployeeId(updateEmployee.getId());
            updateUser.setRole(new RoleDAO().getRole(employeeDetails));
            appUserRepository.saveAndFlush(updateUser);
        }

        if (!updateEmployee.getDepartment().toString().equals(employeeDetails.getDepartment())) {
            updateEmployee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(employeeDetails));
            updateEmployee.setProject(projectRepository.findById(1));
        }


        logger.info("Employee information was updated: {}", updateEmployee);

        return employeeRepository.save(updateEmployee);
    }

}
