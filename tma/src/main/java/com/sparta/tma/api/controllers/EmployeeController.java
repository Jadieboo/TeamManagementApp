package com.sparta.tma.api.controllers;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Role;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/access")
    public String testEmployeeAccess() {
        return "Successful";
    }









}
