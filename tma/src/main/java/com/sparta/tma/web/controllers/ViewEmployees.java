package com.sparta.tma.web.controllers;

import com.sparta.tma.entities.Employee;
import com.sparta.tma.services.ViewEmployeesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewEmployees {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ViewEmployeesService viewEmployeesService;

    @GetMapping("/employees")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllEmployeesForAdmin(Model model) {
        logger.info("view employees admin access active");

        List<Employee> allEmployeesList = viewEmployeesService.getAllEmployees();

        model.addAttribute("employeeList", allEmployeesList);

        return "adminViewAllEmployees";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Employee> getEmployeesForManager() {
        return viewEmployeesService.getEmployeesForManager();
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<Employee> getEmployeesForEmployee() {
        return viewEmployeesService.getEmployeesForEmployee();
    }
}
