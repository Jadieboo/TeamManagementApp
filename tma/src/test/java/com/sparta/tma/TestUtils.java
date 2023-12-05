package com.sparta.tma;

import com.sparta.tma.dtos.EmployeeDTO;

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

    public EmployeeDTO setEmployeeDetails(String fname, String lname, String role, String dept, String project) {
        EmployeeDTO testEmployeeDetails = new EmployeeDTO();
        testEmployeeDetails.setFirstName(fname);
        testEmployeeDetails.setLastName(lname);
        testEmployeeDetails.setRole(role);
        testEmployeeDetails.setDepartment(dept);
        testEmployeeDetails.setProject(project);

        return testEmployeeDetails;
    }
}
