package com.sparta.tma.Repositories;

import com.sparta.tma.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findById(int id);
    Department findByDepartmentIgnoreCase(String department);
}