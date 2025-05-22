package com.sparta.tma.daos;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateEmployeeDAO extends employeeDAO {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final EmployeeRepository employeeRepository;

    @Autowired
    public UpdateEmployeeDAO(DepartmentDAO departmentDAO, ProjectDAO projectDAO, EmployeeRepository employeeRepository) {
        super(departmentDAO, projectDAO);
        this.employeeRepository = employeeRepository;
    }


    public Employee updateEmployeeDetails(EmployeeDTO employeeDetails) {
        logger.info("update employee details method active in UpdateEmployeeDAO");

        Employee employee = employeeRepository.findEmployeeById(employeeDetails.getId());
        logger.info("original employee {}", employee);

        if (isFieldUpdated(employee.getFirstName(), employeeDetails.getFirstName())) {
            logger.info("firstname is updated");

            employee.setFirstName(getFormattedFirstName(employeeDetails));
        }

        if (isFieldUpdated(employee.getLastName(), employeeDetails.getLastName())) {
            logger.info("lastname is updated");

            employee.setLastName(getFormattedLastName(employeeDetails));
        }

        if (isFieldUpdated(employee.getRole().name(), employeeDetails.getRole())) {
            logger.info("role is updated");

            employee.setRole(new RoleDAO().getRole(employeeDetails));
        }

        if (isFieldUpdated(employee.getDepartment().getDepartment(), employeeDetails.getDepartment())) {
            logger.info("department is updated");

            employee.setDepartment(departmentDAO.getDepartment(employeeDetails));
        }

        if (isFieldUpdated(employee.getProject().getProject(), employeeDetails.getProject())) {
            logger.info("project is updated");

            employee.setProject(projectDAO.getProject(employeeDetails));
        }

        logger.info("updated employee {}", employee);

        return employee;
    }

    public Employee updateAssignedProjectToEmployee(int employeeId, EmployeeDTO employeeDetails) {
        logger.info("update employee's assigned project method active inside UpdateEmployeeDAO");

        Employee updateEmployee = employeeRepository.findEmployeeById(employeeId);

        updateEmployee.setProject(projectDAO.getProject(employeeDetails));

        return updateEmployee;
    }

    private boolean isFieldUpdated(String dbValue, String formData) {
        logger.info("checking if field is updated");

        if (formData == null || formData.isEmpty() || formData.isBlank()) return false;

        return !dbValue.trim().equalsIgnoreCase(formData.trim());
    }

}
