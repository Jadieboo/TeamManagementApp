package com.sparta.tma.web.controllers;

import com.sparta.tma.daos.UpdateEmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.exceptions.EmployeeNotFoundException;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import com.sparta.tma.utils.PopulateEmployeeAttributes;
import com.sparta.tma.utils.PopulateModelAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UpdateEmployeeDetails {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final PopulateModelAttributes modelUtil;
    private final EmployeeRepository employeeRepository;
    private final AppUserRepository appUserRepository;
    private final UpdateEmployeeDAO updateEmployeeDAO;

    // TODO - check this works after the big refactor - check tests too
    @Autowired
    public UpdateEmployeeDetails(PopulateModelAttributes modelUtil, EmployeeRepository employeeRepository, AppUserRepository appUserRepository, UpdateEmployeeDAO updateEmployeeDAO) {
        this.modelUtil = modelUtil;
        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
        this.updateEmployeeDAO = updateEmployeeDAO;
    }

    @GetMapping("/admin/view/employees/update/{id}")
    public String updateEmployeeDetails(@PathVariable int id, Model model, Principal principal) {
        logger.info("GET request for admin update employee details is active");

        AppUser user = appUserRepository.findByUsername(principal.getName()).get();
        modelUtil.getAuthorityRoleModelAttribute(model, user); // might not need

        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            logger.info("employee is not present");
            model.addAttribute("not_found", true);
            throw new EmployeeNotFoundException("Employee not found");
        }

        modelUtil.initializeEmployeeDetailsFormModel(model);
        model.addAttribute("employee", employee);

        return "update-employee";
    }

    @Transactional
    @PatchMapping("/admin/update/employees/{id}")
    public String updateEmployeeDetailsFormData(@PathVariable int id, @ModelAttribute("employeeDetails") EmployeeDTO employeeDetails, Model model, Principal principal) {
        logger.info("PATCH request active for admin update employee details by id form data");
        logger.info("employeeDetails: {}", employeeDetails.toString());

        AppUser user = appUserRepository.findByUsername(principal.getName()).get();
        modelUtil.getAuthorityRoleModelAttribute(model, user);

        Employee updatedEmployee = updateEmployeeDAO.updateEmployeeDetails(employeeDetails);

        logger.info("saving updated employee to database: {}", updatedEmployee);
        employeeRepository.saveAndFlush(updatedEmployee);

        model.addAttribute("employee", updatedEmployee);

        return "view-employee-details";
    }
}
