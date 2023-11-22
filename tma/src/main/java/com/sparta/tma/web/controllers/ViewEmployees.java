package com.sparta.tma.web.controllers;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.services.ViewEmployeesService;
import com.sparta.tma.utils.PopulateModelAttributes;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
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
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PopulateModelAttributes modelUtil;

    /**
     * ADMIN ACCESS
     */
    @GetMapping("/admin/view/employees")
    public String getAllEmployeesForAdmin(Model model, Authentication authentication) {
        logger.info("view employees for admin GET method active");

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        modelUtil.getRoleModelAttribute(model, user);

        List<Employee> allEmployeesList = viewEmployeesService.getAllEmployees();

        if (!allEmployeesList.isEmpty()) {
            allEmployeesList.remove(user.getEmployee());
        }

        getPopulatedResultsModelAttribute(model, allEmployeesList);

        return "viewEmployees";
    }

    @GetMapping("/admin/view/employees/{id}")
    public String adminViewEmployeeById(@PathVariable int id, Model model, Principal principal) {
        logger.info("view employee by id GET method active");
        AppUser user = appUserRepository.findByUsername(principal.getName()).get();

        Optional<Employee> employeeOptional = Optional.ofNullable(employeeRepository.findEmployeeById(id));

        modelUtil.getRoleModelAttribute(model, user);

        if (employeeOptional.isEmpty()) {
            logger.info("employee is not present");
            model.addAttribute("not_found", true);
            return "status-code";
        }

        Employee employee = employeeOptional.get();

        model.addAttribute("employee", employee);

        return "view-employee-details";
    }


    /**
     * MANAGER ACCESS
     */
    @GetMapping("/manager/view/employees")
    public String getEmployeesForManager(Model model, Authentication authentication) {
        logger.info("view employees for manager GET method");

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        if (user != null && user.getRole() != null) {
            modelUtil.getRoleModelAttribute(model, user);
        }

        Department department = user.getEmployee().getDepartment();

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(department);

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        getPopulatedResultsModelAttribute(model, employeeList);

        return "viewEmployees";
    }

    @GetMapping("/manager/view/employees/{id}")
    public String viewEmployeeById(@PathVariable int id, Model model, Principal principal) {
        AppUser user = appUserRepository.findByUsername(principal.getName()).get();

        Optional<Employee> employeeOptional = Optional.ofNullable(employeeRepository.findEmployeeById(id));

        if (employeeOptional.isEmpty()) {
            logger.info("employee is not present");
            model.addAttribute("not_found", true);
            modelUtil.getRoleModelAttribute(model, user);
            return "status-code";
        }

        Employee employee = employeeOptional.get();

        if (!employee.getDepartment().getId().equals(user.getEmployee().getDepartment().getId())) {
            logger.info("user department: {}, does not match employee department: {}", user.getEmployee().getDepartment().getDepartment(), employee.getDepartment().getDepartment());
            model.addAttribute("not_authorised", true);
            modelUtil.getRoleModelAttribute(model, user);
            return "status-code";

        }
        model.addAttribute("employee", employee);

        return "view-employee-details";
    }


    /**
     * EMPLOYEE ACCESS
     */

    // all colleagues with role employee, incl manager and admins
    @GetMapping("/employee/view/colleagues")
    public String getColleaguesForEmployee(Model model, Authentication authentication) {
        logger.info("view colleagues for employee GET method");

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        if (user != null && user.getRole() != null) {
            modelUtil.getRoleModelAttribute(model, user);
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
