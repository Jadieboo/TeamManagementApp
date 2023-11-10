package com.sparta.tma.services;

import com.sparta.tma.daos.RoleDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Creates a new user account when a new employee is created
     *
     * The username will be a generated company email based upon the employee's name and employee ID
     *
     * A standard password is generated based on users first and last name initials, followed by 123
     *
     * USERNAME EG) janedoe1387@company.com
     * PASSWORD EG) jd123
     *
     */

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
