package com.sparta.tma.web;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.services.ViewEmployeesService;
import com.sparta.tma.utils.PopulateModelAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ViewEmployeesService viewEmployeesService;
    private final PopulateModelAttributes modelUtil;

    @Autowired
    public LoginController(ViewEmployeesService viewEmployeesService, PopulateModelAttributes modelUtil) {
        this.viewEmployeesService = viewEmployeesService;
        this.modelUtil = modelUtil;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(name="error", required = false) String loginError,
                            @RequestParam(name="logout", required = false) String loggedOut,
                            Model model
    ) {
        if (loginError != null) {
            model.addAttribute("loginError", true);
        }

        if (loggedOut != null){
            model.addAttribute("logoutMessage", true);
        }

        return "login";
    }

    @GetMapping("/admin/homepage")
    public String adminHomepage(Model model, Authentication authentication) {
        logger.info("admin homepage get method active");

        AppUser user = ((AppUser) authentication.getPrincipal());
        Employee employee = user.getEmployee();
        logger.info("Employee: {}", employee);

        model.addAttribute("employee", employee);
        modelUtil.getAuthorityRoleModelAttribute(model, (AppUser) authentication.getPrincipal());

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(employee.getDepartment());

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        modelUtil.getPopulatedResultsModelAttribute(model, employeeList);

        return "adminHomepage";
    }

    @GetMapping("/manager/homepage")
    public String managerHomepage(Model model, Authentication authentication) {
        logger.info("manager homepage get method active");

        AppUser user = ((AppUser) authentication.getPrincipal());
        Employee employee = user.getEmployee();
        logger.info("Employee: {}", employee);

        model.addAttribute("employee", employee);
        modelUtil.getAuthorityRoleModelAttribute(model, (AppUser) authentication.getPrincipal());

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(employee.getDepartment());

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        modelUtil.getPopulatedResultsModelAttribute(model, employeeList);

        return "managerHomepage";
    }

    @GetMapping("/employee/homepage")
    public String employeeHomepage(Model model, Authentication authentication) {
        logger.info("employee homepage get method active");

        AppUser user = ((AppUser) authentication.getPrincipal());
        Employee employee = user.getEmployee();
        logger.info("Employee: {}", employee);


        model.addAttribute("employee", employee);
        modelUtil.getAuthorityRoleModelAttribute(model, (AppUser) authentication.getPrincipal());

        List<Employee> employeeList = viewEmployeesService.getEmployeesByDepartment(employee.getDepartment());

        if (!employeeList.isEmpty()) {
            employeeList.remove(user.getEmployee());
        }

        modelUtil.getPopulatedResultsModelAttribute(model, employeeList);

        return "employeeHomepage";
    }

}
