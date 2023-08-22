package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Project;
import com.sparta.tma.repositories.ProjectRepository;

import java.util.HashMap;

public class ProjectDAO {

    private final ProjectRepository projectRepository;

    public ProjectDAO(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProject(EmployeeDTO employeeDetails) {
        // TODO: handle if project is empty or null

        if (employeeDetails.getProject() == null || employeeDetails.getProject().isBlank()) return projectRepository.findById(1);

        String project = employeeDetails.getProject().trim().toLowerCase();

        HashMap<Integer, String> projectsMap = new HashMap<>();

        for ( Project p : projectRepository.findAll()) {
            projectsMap.put(p.getId(), p.getProject().trim().toLowerCase());
        }

        if (!projectsMap.containsValue(project)) throw new IllegalArgumentException("Could not find project called \"" + project + "\" in database");

        return projectRepository.findByProjectIgnoreCase(project);
    }
}
