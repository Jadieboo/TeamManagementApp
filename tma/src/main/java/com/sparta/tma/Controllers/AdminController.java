package com.sparta.tma.Controllers;

import com.sparta.tma.DAOs.DepartmentDAO;
import com.sparta.tma.DAOs.EmployeeDAO;
import com.sparta.tma.DAOs.RoleDAO;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.Entities.Employee;
import com.sparta.tma.Repositories.DepartmentRepository;
import com.sparta.tma.Repositories.EmployeeRepository;
import com.sparta.tma.Repositories.ProjectRepository;
import com.sparta.tma.Repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EmployeeRepository eRepo;
    @Autowired
    RoleRepository rRepo;
    @Autowired
    DepartmentRepository dRepo;
    @Autowired
    ProjectRepository pRepo;

    @GetMapping("employees")
    public List<Employee> viewAllEmployees() {

        return eRepo.findAll();
    }

    // admin access only
    @Transactional
    @PostMapping("employees/new")
    public Employee createNewEmployee(@RequestBody EmployeeDTO jsonBody) {
        Employee newEmployee = new EmployeeDAO(eRepo).newEmployee(jsonBody);
        newEmployee.setRole(new RoleDAO(rRepo).getRoleDao(jsonBody));
        newEmployee.setDepartment(new DepartmentDAO(dRepo).getDepartmentDao(jsonBody));
        newEmployee.setProject(pRepo.findById(1));
        logger.info("admin created new employee" + newEmployee);

        return eRepo.save(newEmployee);
    }

    @Transactional
    @PatchMapping("employees/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody EmployeeDTO jsonBody) {
        Employee updateEmployee = eRepo.findEmployeeById(id);
        logger.info("Changes being made to " + updateEmployee);

        updateEmployee.setDepartment(new DepartmentDAO(dRepo).getDepartmentDao(jsonBody));
        updateEmployee.setProject(pRepo.findById(1));

        logger.info("Employee department and project was updated: {}", updateEmployee);

        return eRepo.save(updateEmployee);
    }

}
