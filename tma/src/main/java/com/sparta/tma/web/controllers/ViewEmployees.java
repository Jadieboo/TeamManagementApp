package com.sparta.tma.web.controllers;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.exceptions.EmployeeNotFoundException;
import com.sparta.tma.exceptions.UnauthorizedAccessException;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.services.ViewEmployeesService;
import com.sparta.tma.utils.PopulateModelAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewEmployees {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final ViewEmployeesService viewEmployeesService;
    private final AppUserRepository appUserRepository;
    private final EmployeeRepository employeeRepository;
    private final PopulateModelAttributes modelUtil;

    @Autowired
    public ViewEmployees(ViewEmployeesService viewEmployeesService, AppUserRepository appUserRepository, EmployeeRepository employeeRepository, PopulateModelAttributes modelUtil) {
        this.viewEmployeesService = viewEmployeesService;
        this.appUserRepository = appUserRepository;
        this.employeeRepository = employeeRepository;
        this.modelUtil = modelUtil;
    }

    /**
     * ADMIN ACCESS
     */
    @GetMapping("/admin/view/employees")
    public String getAllEmployeesForAdmin(Model model, Authentication authentication) {
        logger.info("view employees for admin GET method active");

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        modelUtil.getAuthorityRoleModelAttribute(model, user);

        List<Employee> allEmployeesList = viewEmployeesService.getAllEmployees();

        if (!allEmployeesList.isEmpty()) {
            allEmployeesList.remove(user.getEmployee());
        }

        modelUtil.getPopulatedResultsModelAttribute(model, allEmployeesList);

        return "viewEmployees";
    }

    @GetMapping("/admin/view/employees/{id}")
    public String adminViewEmployeeById(@PathVariable int id, Model model, Principal principal) {
        logger.info("view employee by id GET method active");
        AppUser user = appUserRepository.findByUsername(principal.getName()).get();

        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found");
        }

        modelUtil.getAuthorityRoleModelAttribute(model, user);

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
            modelUtil.getAuthorityRoleModelAttribute(model, user);
        }

        Department department = user.getEmployee().getDepartment();

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(department);

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        modelUtil.getPopulatedResultsModelAttribute(model, employeeList);

        return "viewEmployees";
    }

    @GetMapping("/manager/view/employees/{id}")
    public String viewEmployeeById(@PathVariable int id, Model model, Principal principal) {
        logger.info("manager view employee details by id GET request active");

        AppUser user = appUserRepository.findByUsername(principal.getName()).get();
        modelUtil.getAuthorityRoleModelAttribute(model, user);

        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            logger.info("Employee not found");
            throw new EmployeeNotFoundException("Employee not found");
        }

        if (!employee.getDepartment().getId().equals(user.getEmployee().getDepartment().getId())) {
            logger.info("User not authorized to view this employee");
            throw new UnauthorizedAccessException("Sorry, you are not authorised to view this page. Please contact your administrator");
        }

        model.addAttribute("employee", employee);

        return "view-employee-details";
    }


    /**
     * EMPLOYEE ACCESS
     */

    // all colleagues with role employee, incl manager and admins
    @GetMapping("/employee/view/colleagues")
    public String viewColleaguesForEmployee(Model model, Authentication authentication) {
        logger.info("view colleagues for employee GET method");

        AppUser user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername()).get();

        if (user != null && user.getRole() != null) {
            modelUtil.getAuthorityRoleModelAttribute(model, user);
        }

        Department department = user.getEmployee().getDepartment();

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(department);

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        modelUtil.getPopulatedResultsModelAttribute(model, employeeList);

        return "viewEmployees";
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
