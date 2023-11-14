package com.sparta.tma.web.controllers;

import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UpdateProjects {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/manager/view/employees/update/{id}")
    public String assignProjectToEmployee(@PathVariable int id, Model model) {
        Employee employee = employeeRepository.findEmployeeById(id);
        model.addAttribute("employee", employee);

        return "manager-update-employee";
    }

}
