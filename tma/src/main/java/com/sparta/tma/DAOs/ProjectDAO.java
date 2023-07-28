package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.Project;
import com.sparta.tma.repositories.ProjectRepository;

public class ProjectDAO {

    private ProjectRepository repo;

    public ProjectDAO(ProjectRepository repo) {
        this.repo = repo;
    }

    public Project getProjectDao(EmployeeDTO jsonBody) {
        return repo.findByProjectIgnoreCase(jsonBody.getProject());
    }
}
