package com.sparta.tma;

import com.sparta.tma.DTOs.EmployeeDTO;

public class TestUtils {

    public EmployeeDTO employeeDetails() {
        EmployeeDTO testEmployeeDetails = new EmployeeDTO();
        testEmployeeDetails.setFirstName("Unit");
        testEmployeeDetails.setLastName("Test");
        testEmployeeDetails.setRole("Admin");
        testEmployeeDetails.setDepartment("Development");
        testEmployeeDetails.setProject("Web App");

        return testEmployeeDetails;
    }
}
