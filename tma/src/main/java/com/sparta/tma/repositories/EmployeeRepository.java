package com.sparta.tma.repositories;

import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findEmployeeById(int id);

    List<Employee> findAllByDepartment(Department department);

    
}