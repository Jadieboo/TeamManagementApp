package com.sparta.tma.daos;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Project;
import com.sparta.tma.repositories.ProjectRepository;
import org.hibernate.annotations.CollectionTypeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ProjectDAO {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectDAO(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProject(EmployeeDTO employeeDetails) throws IllegalArgumentException {

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
