package com.sparta.tma.services;

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
        logger.atTrace();
        List<Employee> employeeList = employeeRepository.findAll();

        if (employeeList.size() < 1) {
            logger.error("Employee list is empty, should be a minimum of one. {}", employeeList);
        } else {
            logger.info("list size of all employees: {}", employeeList.size());
        }

        return (!employeeList.isEmpty() ? employeeList : Collections.emptyList());
    }

    public List<Employee> getEmployeesForManager() {

        return null;
    }

    public List<Employee> getEmployeesForEmployee() {

        return null;
    }
}
