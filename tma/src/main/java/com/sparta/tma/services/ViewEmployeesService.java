package com.sparta.tma.services;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ViewEmployeesService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        logger.info("In the view employees service > get all employees method active");

        List<Employee> employeeList = employeeRepository.findAll();

        return (!employeeList.isEmpty() ? employeeList : Collections.emptyList());
    }

    public List<Employee> getEmployeesByDepartment(Department department) {
        logger.info("In the view employees service > get employees by department method active");

        List<Employee> employeeList = employeeRepository.findAllByDepartment(department);

        return (!employeeList.isEmpty() ? employeeList : Collections.emptyList());
    }

    public List<Employee> getEmployeesForEmployee() {

        return null;
    }
}
