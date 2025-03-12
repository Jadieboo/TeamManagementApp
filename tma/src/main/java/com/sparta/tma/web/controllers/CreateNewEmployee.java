package com.sparta.tma.web.controllers;

import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.exceptions.EmployeeNotFoundException;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import com.sparta.tma.services.RegistrationService;
import com.sparta.tma.services.UserAccountService;
import com.sparta.tma.utils.PopulateModelAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateNewEmployee {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PopulateModelAttributes modelUtil;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final EmployeeRepository employeeRepository;
    private final UserAccountService userAccountService;
    private final RegistrationService registrationService;

    @Autowired
    public CreateNewEmployee(PopulateModelAttributes modelUtil, DepartmentRepository departmentRepository, ProjectRepository projectRepository, AppUserRepository appUserRepository, EmployeeRepository employeeRepository, UserAccountService userAccountService, RegistrationService registrationService) {
        this.modelUtil = modelUtil;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.appUserRepository = appUserRepository;
        this.employeeRepository = employeeRepository;
        this.userAccountService = userAccountService;
        this.registrationService = registrationService;
    }

    @GetMapping("/admin/new/employees")
    public String newEmployees(Model model) {
        modelUtil.initializeEmployeeDetailsFormModel(model);
        return "adminCreateNewEmployee";
    }


    //TODO: create a registration service - done
    // and turn employee dao into a service or component - done but need to fix test
    // need to fix tests for projectdao and department dao too
    // also check tests for services as i have refactored those, check chatgpt's help on tests and mock services from 23/02/2025

    @Transactional
    @PostMapping("/admin/web/register/employees")
    public String processingNewEmployeeForm(@ModelAttribute("employeeDetails") EmployeeDTO employeeDetails, Model model) {
        logger.info("employeeDetails: {}", employeeDetails);

        try {
            Employee employee = registrationService.registerEmployee(employeeDetails);

            model.addAttribute("showConfirmation", true);
            model.addAttribute("formEmployee", employee.getFirstName() + " " + employee.getLastName());

        } catch (Exception e) {
            logger.error("Error registering employee ", e);
            model.addAttribute("showConfirmation", false);
        }

        modelUtil.initializeEmployeeDetailsFormModel(model);

        return "adminCreateNewEmployee";
    }


}
