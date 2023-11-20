package com.sparta.tma.web.controllers;

import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import com.sparta.tma.utils.PopulateEmployeeAttributes;
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

    // TODO: find out why this is null
    //  also for create new employee web controller

    @Autowired
    private PopulateEmployeeAttributes populateUtil;
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

        EmployeeDTO employeeDetails = new EmployeeDTO();
        model.addAttribute("employeeDetails", employeeDetails);
        model.addAttribute("projectList", populateUtil.populateDepartmentOptions());


        return "manager-update-employee";
    }

    //TODO implement form on front end
    @Transactional
    @PatchMapping("/manager/update/employees/{id}")
    public String updateEmployeeAssignedProject(@PathVariable int id, @ModelAttribute("employeeDetails") EmployeeDTO employeeDetails, Model model) {
        Employee employee = new EmployeeDAO(employeeRepository, projectRepository).updateAssignedProjectToEmployee(id, employeeDetails);

        Employee savedEmployee = employeeRepository.save(employee);

        model.addAttribute("employee", savedEmployee);
        model.addAttribute("projectList", populateUtil.populateProjectOptions());

        return "view-employee-details";
    }


}
