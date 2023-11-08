package com.sparta.tma.web.controllers;

import com.sparta.tma.entities.AppUser;
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

    @GetMapping("/admin/view/employees")
    public String getAllEmployeesForAdmin(Model model, Authentication authentication) {
        logger.info("view employees admin access active");

        Optional<AppUser> user = appUserRepository.findByUsername(authentication.getName());

        List<Employee> allEmployeesList = viewEmployeesService.getAllEmployees(user.get());

        model.addAttribute("employeeList", allEmployeesList);

        return "adminViewAllEmployees";
    }

    @GetMapping("/manager/view/employees")
    public String getEmployeesForManager() {
        List<Employee> list = viewEmployeesService.getEmployeesForManager();
        return "welcome";
    }

    @GetMapping("/employee/view/colleagues")
    public String getEmployeesForEmployee() {
        List<Employee> list = viewEmployeesService.getEmployeesForEmployee();

        return "welcome";
    }
}
