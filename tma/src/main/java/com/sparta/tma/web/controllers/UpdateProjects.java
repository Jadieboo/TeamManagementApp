package com.sparta.tma.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UpdateProjects {

    @GetMapping("/manager/view/employees/update/{id}")
    public String assignProjectToEmployee(@PathVariable int id) {
        return null;
    }

}
