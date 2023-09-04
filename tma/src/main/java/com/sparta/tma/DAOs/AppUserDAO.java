package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.repositories.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AppUserDAO {
    private final PasswordEncoder encoder;
    private final EmployeeRepository employeeRepository;

    public AppUserDAO(PasswordEncoder encoder, EmployeeRepository employeeRepository) {
        this.encoder = encoder;
        this.employeeRepository = employeeRepository;
    }

    public AppUser createNewAppUser(EmployeeDTO employeeDetails, Integer id) throws NullPointerException, IllegalArgumentException {

        if (id == null) {
            throw new NullPointerException("Employee ID number is null");
        } else if (id <= 0) {
            throw new IllegalArgumentException("Employee number cannot be 0 or less");
        }

        AppUser user = new AppUser();
        user.setId(0L);
        user.setUsername(createUserUsername(employeeDetails, id));
        user.setPassword(encoder.encode(createUserPassword(employeeDetails)));
        user.setRole(new RoleDAO().getRole(employeeDetails));
        user.setEmployee(employeeRepository.findEmployeeById(id));

        return user;
    }

    public String createUserUsername(EmployeeDTO employeeDetails, int employeeId) {
        String fName = employeeDetails.getFirstName()
                .trim()
                .toUpperCase()
                .substring(0,1);

        String lName = employeeDetails.getLastName()
                .trim()
                .toLowerCase();

        return fName + lName + employeeId + "@company.com";
    }
    public String createUserPassword(EmployeeDTO employeeDetails) {
        String fName = employeeDetails.getFirstName()
                .trim()
                .toLowerCase()
                .substring(0,1);

        String lName = employeeDetails.getLastName()
                .trim()
                .toLowerCase()
                .substring(0,1);

        return fName + lName + 123;
    }


}
