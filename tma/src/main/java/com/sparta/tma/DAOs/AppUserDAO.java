package com.sparta.tma.DAOs;

import com.sparta.tma.DTOs.EmployeeDTO;

public class AppUserDAO {

    public String createTempUsername(EmployeeDTO employeeDetails) {
        String fName = employeeDetails.getFirstName()
                .trim()
                .toUpperCase()
                .substring(0,1);

        String lName = employeeDetails.getLastName()
                .trim()
                .toLowerCase();

        return fName + lName + "Temp@company.com";
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
