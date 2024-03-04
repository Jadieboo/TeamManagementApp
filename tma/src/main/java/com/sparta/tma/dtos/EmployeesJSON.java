package com.sparta.tma.dtos;

import java.util.List;

public class EmployeesJSON {
    public List<EmployeeDTO> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeDTO> employeeList) {
        this.employeeList = employeeList;
    }

    private List<EmployeeDTO> employeeList;


}
