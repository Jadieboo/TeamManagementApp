package com.sparta.tma.web.controllers;

import com.sparta.tma.daos.UpdateEmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.exceptions.EmployeeNotFoundException;
import com.sparta.tma.exceptions.UnauthorizedAccessException;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
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
public class UpdateAssignedProjects {
    Logger logger = LoggerFactory.getLogger(getClass());

    // TODO: find out why this is null
    //  also for create new employee web controller

    private final PopulateEmployeeAttributes employeeUtil;
    private final PopulateModelAttributes modelUtil;
    private final EmployeeRepository employeeRepository;
    private final AppUserRepository appUserRepository;
    private final UpdateEmployeeDAO updateEmployeeDAO;
    @Autowired
    public UpdateAssignedProjects(PopulateEmployeeAttributes employeeUtil, PopulateModelAttributes modelUtil, UpdateEmployeeDAO updateEmployeeDAO, EmployeeRepository employeeRepository, AppUserRepository appUserRepository) {
        this.employeeUtil = employeeUtil;
        this.modelUtil = modelUtil;
        this.updateEmployeeDAO = updateEmployeeDAO;
        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/manager/view/employees/update/{id}")
    public String updateEmployeeDetailsPage(@PathVariable int id, Model model, Principal principal) {
        logger.info("GET request for manager update employee by id active");

        AppUser user = appUserRepository.findByUsername(principal.getName()).get();
        modelUtil.getAuthorityRoleModelAttribute(model, user);

        Employee employee = employeeRepository.findEmployeeById(id);

        if (employee == null) {
            logger.info("employee is not present");
            model.addAttribute("not_found", true);
            throw new EmployeeNotFoundException("Employee not found");
        }

        if (!employee.getDepartment().getId().equals(user.getEmployee().getDepartment().getId())) {
            logger.info("user department: {}, does not match employee department: {}", user.getEmployee().getDepartment().getDepartment(), employee.getDepartment().getDepartment());
            model.addAttribute("not_authorised", true);
            throw new UnauthorizedAccessException("Sorry, you are not authorised to view this page. Please contact your administrator");
        }

        model.addAttribute("employee", employee);
        logger.info("employee project {}", employee.getProject());

        EmployeeDTO employeeDetails = new EmployeeDTO();
        model.addAttribute("employeeDetails", employeeDetails);
        model.addAttribute("projectList", employeeUtil.populateProjectOptions());

        return "manager-update-employee";
    }

    @Transactional
    @PatchMapping("/manager/update/employees/{id}")
    public String updateEmployeeAssignedProject(@PathVariable int id, @ModelAttribute("employeeDetails") EmployeeDTO employeeDetails, Model model, Principal principal) {
        logger.info("PATCH request for manager update employee project by id active");

        AppUser user = appUserRepository.findByUsername(principal.getName()).get();
        modelUtil.getAuthorityRoleModelAttribute(model, user);

        Employee savedEmployee = employeeRepository.save(updateEmployeeDAO.updateAssignedProjectToEmployee(id, employeeDetails));
        logger.info("saved employee with new assigned project {}", savedEmployee.getProject());

        model.addAttribute("employee", savedEmployee);
        model.addAttribute("projectList", employeeUtil.populateProjectOptions());

        logger.info("model attributes set for updating employee's project {}", model);

        return "view-employee-details";
    }


}
