package com.sparta.tma.Repositories;

import com.sparta.tma.Entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findById(int id);
    Project findByProjectIgnoreCase(String project);
}