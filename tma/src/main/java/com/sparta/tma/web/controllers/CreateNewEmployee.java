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
import com.sparta.tma.services.UserAccountService;
import com.sparta.tma.utils.PopulateEmployeeAttributes;
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

    // TODO: find out why this is null
    private PopulateEmployeeAttributes populateUtil = new PopulateEmployeeAttributes();

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserAccountService userAccountService;
//    @Autowired
//    private EmployeeDAO employeeDAO;


    @GetMapping("/admin/new/employees")
    public String newEmployees(Model model) {
        initializePageModel(model);
        return "adminCreateNewEmployee";
    }


    //TODO: create a registration service and turn employee dao into a service or component

    @Transactional
    @PostMapping("/admin/web/register/employees")
    public String processingNewEmployeeForm(@ModelAttribute("employeeDetails") EmployeeDTO employeeDetails, Model model) {
        logger.info("employeeDetails: {}", employeeDetails);

        Employee newEmployee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails);

        Employee employee = employeeRepository.saveAndFlush(newEmployee);
        logger.info("New employee saved, {}", employee);

        logger.info("New user is being created");
        AppUser newAppUser = userAccountService.createNewAppUser(employeeDetails, employee.getId());

        AppUser appUser = appUserRepository.saveAndFlush(newAppUser);
        logger.info("New user saved, {}", appUser);

        Employee employeeExists = employeeRepository.findById(employee.getId()).orElseThrow(() -> new EmployeeNotFoundException("Employee " + employee + " not found/created"));

        if (employeeExists == null) {
            model.addAttribute("showConfirmation", false);
        } else {
            model.addAttribute("showConfirmation", true);
            model.addAttribute("formEmployee", employeeExists.getFirstName() + " " + employeeExists.getLastName());
        }

        initializePageModel(model);

        return "adminCreateNewEmployee";
    }

    private void initializePageModel(Model model) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        model.addAttribute("departmentList", populateUtil.populateDepartmentOptions());
        model.addAttribute("roleList", populateUtil.populateRoleOptions());
        model.addAttribute("employeeDetails", employeeDetails);
    }
}
