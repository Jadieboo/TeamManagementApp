package com.sparta.tma.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "project", nullable = false)
    private String project;

    @Override
    public String toString() {
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project1 = (Project) o;
        return Objects.equals(id, project1.id) && Objects.equals(project, project1.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project);
    }
}