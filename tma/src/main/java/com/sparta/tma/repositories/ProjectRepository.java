package com.sparta.tma.repositories;

import com.sparta.tma.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findById(int id);
    Project findByProjectIgnoreCase(String project);
}