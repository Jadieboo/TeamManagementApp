package com.sparta.tma.utils;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class PopulateModelAttributes {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PopulateEmployeeAttributes employeeUtil;

    public void getRoleModelAttribute(Model model, AppUser user) {
        model.addAttribute("isAdmin", false);
        model.addAttribute("isManager", false);
        model.addAttribute("isEmployee", false);

        String role = user.getRole().name().toLowerCase();
        logger.info("user role: {}", role);

        if (role.equals("admin")) {
            model.addAttribute("isAdmin", true);
            logger.info("Setting isAdmin model attribute to true");
        } else if (role.equals("manager")) {
            model.addAttribute("isManager", true);
            logger.info("Setting isManager model attribute to true");
        } else if (role.equals("employee")) {
            model.addAttribute("isEmployee", true);
            logger.info("Setting isEmployee model attribute to true");
        }
    }

    public void initializeCreateNewEmployeePageModel(Model model) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        model.addAttribute("departmentList", employeeUtil.populateDepartmentOptions());
        model.addAttribute("roleList", employeeUtil.populateRoleOptions());
        model.addAttribute("employeeDetails", employeeDetails);
    }
}