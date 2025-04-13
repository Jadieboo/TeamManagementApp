package com.sparta.tma.services;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.exceptions.EmployeeNotFoundException;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final com.sparta.tma.daos.employeeDAO employeeDAO;
    private final EmployeeRepository employeeRepository;
    private final UserAccountService userAccountService;
    private final AppUserRepository appUserRepository;
    @Autowired
    public RegistrationService(com.sparta.tma.daos.employeeDAO employeeDAO, EmployeeRepository employeeRepository, UserAccountService userAccountService, AppUserRepository appUserRepository) {
        this.employeeDAO = employeeDAO;
        this.employeeRepository = employeeRepository;
        this.userAccountService = userAccountService;
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    public Employee registerEmployee(EmployeeDTO employeeDetails) {
        logger.info("registration service > register employee");

        Employee newEmployee = employeeDAO.createNewEmployee(employeeDetails);

        Employee savedEmployee = employeeRepository.save(newEmployee);
        logger.info("New employee saved, {}", savedEmployee);

        logger.info("New user is being created");
        AppUser newAppUser = userAccountService.createNewAppUser(employeeDetails, savedEmployee.getId());

        AppUser savedAppUser = appUserRepository.save(newAppUser);
        logger.info("New user saved, {}", savedAppUser);

        return employeeRepository.findById(savedEmployee.getId()).orElseThrow(()-> new EmployeeNotFoundException("Error creating employee " + savedEmployee));
    }
}
