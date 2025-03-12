package com.sparta.tma.utils;

import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Project;
import com.sparta.tma.entities.Role;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PopulateEmployeeAttributes {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public PopulateEmployeeAttributes(DepartmentRepository departmentRepository, ProjectRepository projectRepository) {
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
    }

    public List<String> populateDepartmentOptions() {
        List<String> departments = new ArrayList<>();

        List<Department> departmentList = departmentRepository.findAll();

        for (Department d : departmentList) {
            departments.add(d.getDepartment());
        }

        logger.info("department list: {}", departments);

        return departments;
    }

    public List<String> populateProjectOptions() {
        List<String> projects = new ArrayList<>();

        List<Project> projectList = projectRepository.findAll();

        for (Project p : projectList) {
            projects.add(p.getProject());
        }

        logger.info("populate project list: {}", projects);

        return projects;
    }

    public List<String> populateRoleOptions() {

        List<String> roles = new ArrayList<>();

          for (Role role : Role.values()) {
              String formatRole = role.name().toUpperCase().charAt(0) + role.name().toLowerCase().substring(1);
              roles.add(formatRole);
          }

        logger.info("roles list: {}", roles);

        return roles;
    }

}
