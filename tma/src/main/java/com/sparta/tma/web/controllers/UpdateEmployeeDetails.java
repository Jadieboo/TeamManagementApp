package com.sparta.tma.web.controllers;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.utils.PopulateEmployeeAttributes;
import com.sparta.tma.utils.PopulateModelAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UpdateEmployeeDetails {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PopulateModelAttributes modelUtil;
    @Autowired
    private PopulateEmployeeAttributes employeeUtil;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/admin/view/employees/update/{id}")
    public String updateEmployeeDetails(@PathVariable int id, Model model, Principal principal) {
        logger.info("GET request for admin update employee details is active");

        AppUser user = appUserRepository.findByUsername(principal.getName()).get();
        modelUtil.getRoleModelAttribute(model, user); // might not need

        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            logger.info("employee is not present");
            model.addAttribute("not_found", true);
            return "status-code";
        }

        model.addAttribute("employee", employee);

//        might not need
//        logger.info("employee project {}", employee.getProject());
//
//        EmployeeDTO employeeDetails = new EmployeeDTO();
//        model.addAttribute("employeeDetails", employeeDetails);
//        model.addAttribute("projectList", employeeUtil.populateProjectOptions());
        return "admin-update-employee";
    }
}
