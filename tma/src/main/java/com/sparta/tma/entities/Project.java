package com.sparta.tma.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "project", nullable = false)
    private String project;

    @Override
    public String toString() {
        return project;
    }

}