package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.Entities.Project;
import com.sparta.tma.Repositories.ProjectRepository;

public class ProjectDAO {

    private ProjectRepository repo;

    public ProjectDAO(ProjectRepository repo) {
        this.repo = repo;
    }

    public Project getProjectDao(EmployeeDTO jsonBody) {
        return repo.findByProjectIgnoreCase(jsonBody.getProject());
    }
}
