package com.sparta.tma.utils;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class PopulateModelAttributes {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final PopulateEmployeeAttributes employeeUtil;
    @Autowired
    public PopulateModelAttributes(PopulateEmployeeAttributes employeeUtil) {
        this.employeeUtil = employeeUtil;
    }

    public void getAuthorityRoleModelAttribute(Model model, AppUser user) {
        // TODO - do i need both isAdmin and role attributes? Am I still using isAdmin, isManager etc

        model.addAttribute("isAdmin", false);
        model.addAttribute("isManager", false);
        model.addAttribute("isEmployee", false);

        String role = user.getRole().name().toLowerCase();
        model.addAttribute("role", role);

        logger.info("user role: {}", role);

        switch (role) {
            case "admin" -> {
                model.addAttribute("isAdmin", true);
                logger.info("Setting admin model attribute");
            }
            case "manager" -> {
                model.addAttribute("isManager", true);
                logger.info("Setting manager model attribute");
            }
            case "employee" -> {
                model.addAttribute("isEmployee", true);
                logger.info("Setting employee model attribute");
            }
        }

        logger.info("role model attr: {}", model.getAttribute("role"));
    }

    public void initializeEmployeeDetailsFormModel(Model model) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        model.addAttribute("departmentList", employeeUtil.populateDepartmentOptions());
        model.addAttribute("projectList", employeeUtil.populateProjectOptions());
        model.addAttribute("roleList", employeeUtil.populateRoleOptions());
        model.addAttribute("employeeDetails", employeeDetails);
    }

    public void getPopulatedResultsModelAttribute(Model model, List<Employee> employeeList) {
        if (employeeList.size() < 1) {
            logger.warn("No employees found");
            model.addAttribute("results_not_found", true);
            model.addAttribute("results_populated", false);
        } else {
            logger.info("list size of all employees: {}", employeeList.size());
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("results_populated", true);
            model.addAttribute("results_not_found", false);
        }
    }


}
