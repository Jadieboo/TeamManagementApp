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

//        TODO: TEST TO SEE WHAT HAPPENS IF I TRY TO CHANGE THE LIST
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setId(0);
//        employeeDTO.setFirstName("jade");
//        employeeDTO.setLastName("sAle");
//        employeeDTO.setRole("admin");
//        employeeDTO.setDepartment("hr");
//
//        Employee newEmployee = new EmployeeDAO().setEmployee_Id_FirstName_LastName(employeeDTO);
//        newEmployee.setRole(new RoleDAO().getRole(employeeDTO.getRole()));
//        newEmployee.setDepartment(new DepartmentDAO(departmentRepository).getDepartmentDao(employeeDTO));
//        newEmployee.setProject(projectRepository.findById(1));
//
//
//        employeeList.add(newEmployee);

        return (!employeeList.isEmpty() ? employeeList : Collections.emptyList());
    }

    // admin access only

    //TODO: this does not work when used with postman, throws a null pointer exception
    // BEFORE you work on this, upload to git hub! That the initial authorisation is working
//    Cannot invoke "com.sparta.tma.repositories.DepartmentRepository.findByDepartmentIgnoreCase(String)" because "this.repo" is null
    @Transactional
    @PostMapping("admin/register/employees")
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDetails) {

        Employee newEmployee = new EmployeeDAO(employeeRepository, departmentRepository, projectRepository).createNewEmployee(employeeDetails);

        Employee employee = employeeRepository.saveAndFlush(newEmployee);
        logger.info("New employee saved, {}", employee);


        logger.info("New user is being created");
        // TODO: create a method to handle creating an app user
        AppUser newAppUser = new AppUser();
        newAppUser.setId(0L);
        //TODO: research if its good practice to do dependency injection like this, or just pass dto through as param and have the method handle the details??
        newAppUser.setUsername(new AppUserDAO().createUserUsername(employeeDetails, employee.getId()));
        newAppUser.setPassword(encoder.encode(new AppUserDAO().createUserPassword(employeeDetails)));
        newAppUser.setRole(new RoleDAO().getRole(employeeDetails));
        newAppUser.setEmployee(employeeRepository.findEmployeeById(employee.getId()));

        //save new user
        AppUser appUser = appUserRepository.saveAndFlush(newAppUser);
        logger.info("New user saved, {}", appUser);


        //TODO: hide password in the response.
        return employeeRepository.findById(employee.getId()).orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee {} not found/created", employee)));

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
