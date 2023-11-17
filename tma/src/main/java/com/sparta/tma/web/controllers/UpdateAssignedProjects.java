package com.sparta.tma.web.controllers;

import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UpdateAssignedProjects {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/manager/view/employees/update/{id}")
    public String updateEmployeeDetailsPage(@PathVariable int id, Model model) {
        Employee employee = employeeRepository.findEmployeeById(id);
        model.addAttribute("employee", employee);

        return "manager-update-employee";
    }

    @Transactional
    @PatchMapping("/manager/update/employees/{id}")
    public String updateEmployeeAssignedProject(@PathVariable int id, @ModelAttribute("employeeDetails") EmployeeDTO employeeDetails, Model model) {
        Employee employee = new EmployeeDAO(employeeRepository, projectRepository).updateAssignedProjectToEmployee(id, employeeDetails);

        return "";
    }


}
