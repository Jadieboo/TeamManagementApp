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
import java.util.Collections;
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

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        if (user != null && user.getRole() != null) {
            getRoleModelAttribute(model, user);
        }

        List<Employee> allEmployeesList = viewEmployeesService.getAllEmployees();

        if (!allEmployeesList.isEmpty()) {
            allEmployeesList.remove(user.getEmployee());
        }

        getPopulatedResultsModelAttribute(model, allEmployeesList);

        return "viewEmployees";
    }


    /**
     * MANAGER ACCESS
     */
    @GetMapping("/manager/view/employees")
    public String getEmployeesForManager(Model model, Authentication authentication) {
        logger.info("view employees for manager GET method");

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        if (user != null && user.getRole() != null) {
            getRoleModelAttribute(model, user);
        }

        Department department = user.getEmployee().getDepartment();

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(department);

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        getPopulatedResultsModelAttribute(model, employeeList);

        return "viewEmployees";
    }


    /**
     * EMPLOYEE ACCESS
     */

    // all colleagues with role employee, no manager or admins
    @GetMapping("/employee/view/colleagues")
    public String getColleaguesForEmployee(Model model, Authentication authentication) {
        logger.info("view colleagues for employee GET method");

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        if (user != null && user.getRole() != null) {
            getRoleModelAttribute(model, user);
        }

        Department department = user.getEmployee().getDepartment();

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(department);

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        getPopulatedResultsModelAttribute(model, employeeList);

        return "viewEmployees";
    }

    private void getPopulatedResultsModelAttribute(Model model, List<Employee> employeeList) {
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

    private void getRoleModelAttribute(Model model, AppUser user) {
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

//    @GetMapping("/colleagues/project")
//    public String viewAllColleaguesWithinProject(Model model, Authentication authentication) {
//
//        logger.info("inside view all colleagues within project GET method");
//
//        String username = authentication.getName();
//
//        logger.info("authentication: {}, " +
//                "Username: {}", authentication, username);
//
//        Optional<AppUser> user = appUserRepository.findByUsername(username);
//
//        List<Employee> employeeList = employeeRepository.findAllByDepartmentAndProjectWithRoleEmployee(user.get().getEmployee().getDepartment(), user.get().getEmployee().getProject());
//
//        if (!employeeList.isEmpty()) {
//            employeeList.remove(user.get().getEmployee());
//        }
//
//        if (employeeList.size() < 1) {
//            logger.warn("No employees found");
//        } else {
//            logger.info("employee list size: {}", employeeList.size());
//        }
//
//        return (!employeeList.isEmpty() ? employeeList : Collections.emptyList());
//    }
}
