package com.sparta.tma.controllers;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder encoder;


    @GetMapping("/login")
    public String showLoginPage() {
//        model.addAttribute("loginDetails", new AppUserDTO());
        logger.info("login get method is active");
        return "login";
    }

    @GetMapping("admin/homepage")
    public String adminHomepage(Model model, Authentication authentication) {
        logger.info("admin homepage get method active");

        Optional<AppUser> user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername());

        Employee employee = user.get().getEmployee();
        logger.info("Employee: {}", employee);

        model.addAttribute("employee", employee);

        return "adminHomepage";
    }

    @GetMapping("/manager/homepage")
    public String managerHomepage(Model model, Authentication authentication) {
        logger.info("manager homepage get method active");

        Optional<AppUser> user = appUserRepository.findByUsername(((AppUser) authentication.getPrincipal()).getUsername());

        Employee employee = user.get().getEmployee();
        logger.info("Employee: {}", employee);

        model.addAttribute("employee", employee);

        return "managerHomepage";
    }

    @GetMapping("/employee/homepage")
    public String employeeHomepage(Model model, Authentication authentication) {
        logger.info("employee homepage get method active");

        Employee employee = ((AppUser) authentication.getPrincipal()).getEmployee();
        logger.info("Employee: {}", employee);


        model.addAttribute("employee", employee);

        return "employeeHomepage";
    }

    @GetMapping("/welcome")
    public String welcome() {

        return "welcome";
    }

    @GetMapping("/admin/new/employees")
    public String newEmployees() {

        return "adminCreateNewEmployee";
    }

    // TODO: need to clean up login controller class





    // trying to handle LoginSuccessHandler in controller instead of in configuration

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private LoginSuccessHandler loginSuccessHandler;
//
//    @PostMapping("/loginHandler")
//    public String performLogin(HttpServletRequest request, HttpServletResponse response,
//                               @RequestParam String username, @RequestParam String password) throws ServletException, IOException {
//
//        try {
//            // Create an authentication token with the user's credentials
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//
//            // Perform authentication
//            Authentication authentication = authenticationManager.authenticate(token);
//
//            // If authentication is successful, call the custom success handler
//            loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
//
//            // Authentication was successful, so no need to proceed further
//            return null;
//        } catch (AuthenticationException e) {
//            // Handle authentication failure (e.g., show an error message)
//            return "redirect:/login?error";
//        }
//    }


//    @PostMapping("/loginHandler")
//    public String loginHandler(@RequestParam String username, @RequestParam String password, Model model) {
//        logger.info("loginHandler post method is active");
//
//        String url = "redirect:/login?error";
//
//        Optional<AppUser> user = appUserRepository.findByUsername(username);
//
//        if (user.isPresent()) {
//            logger.info("user is present");
//
//            if (encoder.matches(password, user.get().getPassword())) {
//                logger.info("passwords match");
//                switch (user.get().getRole().toString()) {
//                    case "ADMIN":
//                        url = "adminHomepage";
//                        logger.info("user: {}, role is {}", user.get().getUsername(), user.get().getRole().toString());
//                        return adminHomepage(model, user.get());
//
//                    case "MANAGER":
//                        url = "managerHomepage";
//                        logger.info("user: {}, role is {}", user.get().getUsername(), user.get().getRole().toString());
//                        return managerHomepage(model, user.get());
//
//
//                    case "EMPLOYEE":
//                        url = "employee_homepage";
//                        logger.info("user: {}, role is {}", user.get().getUsername(), user.get().getRole().toString());
//                        return employeeHomepage(model, user.get());
//
//                }
//            } else {
//                logger.info("passwords do not match");
//            }
//
//        } else {
//            logger.info("user is not present");
//        }
//
//        return url;
//
//    }


}
