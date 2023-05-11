package com.sparta.tma.Repositories;

import com.sparta.tma.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findEmployeeById(int id);
}