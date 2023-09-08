package com.sparta.tma.controllers;

import com.sparta.tma.DTOs.AppUserDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.services.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder encoder;

    private final Logger logger = LoggerFactory.getLogger(getClass());



    @GetMapping("/loginPage")
    public String login(Model model) {
        model.addAttribute("loginDetails", new AppUserDTO());
        logger.info("login get method is active");
        return "loginPage";
    }

    @PostMapping("/loginPage")
    public String processLogin(@ModelAttribute("loginDetails") AppUserDTO userDetails) {
        logger.info("processLogin post method is active");


        String url = "no-user";

        Optional<AppUser> user = appUserRepository.findByUsername(userDetails.getUsername());

        if (user.isPresent()) {
            logger.info("user is present");

            switch (user.get().getAuthorities().toString()) {
                case "ADMIN" -> url = "admin_homepage";
                case "MANAGER" -> url = "manager_homepage";
                case "EMPLOYEE" -> url = "employee_homepage";
            }
        } else {
            logger.info("user is not present");
        }

        return url;
    }
}
