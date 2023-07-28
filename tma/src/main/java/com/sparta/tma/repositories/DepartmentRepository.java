package com.sparta.tma.repositories;

import com.sparta.tma.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findById(int id);
    Department findByDepartmentIgnoreCase(String department);
}