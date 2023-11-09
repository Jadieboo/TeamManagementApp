package com.sparta.tma.web.controllers;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.services.ViewEmployeesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewEmployees {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ViewEmployeesService viewEmployeesService;
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * ADMIN ACCESS
     */
    @GetMapping("/admin/view/employees")
    public String getAllEmployeesForAdmin(Model model, Authentication authentication) {
        logger.info("view employees for admin GET method active");

        Optional<AppUser> user = appUserRepository.findByUsername(authentication.getName());

        List<Employee> allEmployeesList = viewEmployeesService.getAllEmployees(user.get());

        model.addAttribute("employeeList", allEmployeesList);

        return "adminViewAllEmployees";
    }


    /**
     * MANAGER ACCESS
     */
    @GetMapping("/manager/view/employees")
    public String getEmployeesForManager(Model model, Authentication authentication) {
        logger.info("view employees for manager GET method");

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        Department department = user.getEmployee().getDepartment();

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(department);

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        if (employeeList.size() < 1) {
            logger.warn("No employees found");
        } else {
            logger.info("list size of all employees: {}", employeeList.size());
            model.addAttribute("employeeList", employeeList);
        }

        return "welcome";
    }


    /**
     * EMPLOYEE ACCESS
     */
    @GetMapping("/employee/view/colleagues")
    public String getEmployeesForEmployee() {
        List<Employee> list = viewEmployeesService.getEmployeesForEmployee();

        return "welcome";
    }
}
