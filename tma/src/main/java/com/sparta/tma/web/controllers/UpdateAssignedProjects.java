package com.sparta.tma.web.controllers;

import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
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
public class UpdateAssignedProjects {
    Logger logger = LoggerFactory.getLogger(getClass());

    // TODO: find out why this is null
    //  also for create new employee web controller

    @Autowired
    private PopulateEmployeeAttributes employeeUtil;
    @Autowired
    private PopulateModelAttributes modelUtil;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/manager/view/employees/update/{id}")
    public String updateEmployeeDetailsPage(@PathVariable int id, Model model, Principal principal) {
        logger.info("GET request for manager update employee by id active");
        Employee employee = employeeRepository.findEmployeeById(id);
        AppUser user = appUserRepository.findByUsername(principal.getName()).get();

        if (employee == null) {
            logger.info("employee is not present");
            model.addAttribute("not_found", true);
            modelUtil.getRoleModelAttribute(model, user);
            return "status-code";
        }

        if (!employee.getDepartment().getId().equals(user.getEmployee().getDepartment().getId())) {
            logger.info("user department: {}, does not match employee department: {}", user.getEmployee().getDepartment().getDepartment(), employee.getDepartment().getDepartment());
            model.addAttribute("not_authorised", true);
            modelUtil.getRoleModelAttribute(model, user);
            return "status-code";
        }

        model.addAttribute("employee", employee);
        logger.info("employee project {}", employee.getProject());

        EmployeeDTO employeeDetails = new EmployeeDTO();
        model.addAttribute("employeeDetails", employeeDetails);
        model.addAttribute("projectList", employeeUtil.populateProjectOptions());

        return "manager-update-employee";
    }

    //TODO implement form on front end
    @Transactional
    @PatchMapping("/manager/update/employees/{id}")
    public String updateEmployeeAssignedProject(@PathVariable int id, @ModelAttribute("employeeDetails") EmployeeDTO employeeDetails, Model model) {
        logger.info("PATCH request for manager update employee project by id active");

        Employee savedEmployee = employeeRepository.save(new EmployeeDAO(employeeRepository, projectRepository).updateAssignedProjectToEmployee(id, employeeDetails));
        logger.info("saved employee with new assigned project {}", savedEmployee.getProject());

        model.addAttribute("employee", savedEmployee);
        model.addAttribute("projectList", employeeUtil.populateProjectOptions());

        logger.info("model attributes set for updating employee's project {}", model);

        return "view-employee-details";
    }


}
