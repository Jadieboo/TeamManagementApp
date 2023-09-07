package com.sparta.tma.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @GetMapping("employee/access")
    public String testEmployeeAccess() {
        return "Successful";
    }
}
