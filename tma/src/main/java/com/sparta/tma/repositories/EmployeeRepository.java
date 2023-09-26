package com.sparta.tma.repositories;

import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Project;
import com.sparta.tma.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findEmployeeById(int id);

    List<Employee> findAllByDepartment(Department department);

    List<Employee> findAllByDepartmentAndRole(Department department, Role role);

    @Query("SELECT e FROM Employee e WHERE e.department = :department AND e.project = :project AND e.role = 'EMPLOYEE'")
    List<Employee> findAllByDepartmentAndProjectWithRoleEmployee(@Param("department") Department department, @Param("project") Project project);

}

