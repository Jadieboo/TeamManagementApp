package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Project;
import com.sparta.tma.repositories.ProjectRepository;

public class ProjectDAO {

    private ProjectRepository projectRepository;

    public ProjectDAO(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProject(EmployeeDTO employeeDetails) {
        // TODO: handle if employeeDetails.getProject() is empty
        if (employeeDetails.getProject().isEmpty()) return projectRepository.findById(1);

        return projectRepository.findByProjectIgnoreCase(employeeDetails.getProject());
    }
}
