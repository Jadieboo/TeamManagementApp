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
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "department", nullable = false)
    private String department;

    @Override
    public String toString() {
        return department;
    }
}