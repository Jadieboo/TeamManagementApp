package com.sparta.tma.daos;

import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateEmployeeDAO extends EmployeeDAO {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final EmployeeRepository employeeRepository;

    ProjectDAO projectDAO = new ProjectDAO(projectRepository);
    DepartmentDAO departmentDAO = new DepartmentDAO(departmentRepository);

    public UpdateEmployeeDAO(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ProjectRepository projectRepository) {
        super(departmentRepository, projectRepository);
        this.employeeRepository = employeeRepository;
    }

    public UpdateEmployeeDAO(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        super(projectRepository);
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

            employee.setDepartment(new DepartmentDAO(departmentRepository).getDepartment(employeeDetails));
        }

        if (isFieldUpdated(employee.getProject().getProject(), employeeDetails.getProject())) {
            logger.info("project is updated");

            employee.setProject(new ProjectDAO(projectRepository).getProject(employeeDetails));
        }

        logger.info("updated employee {}", employee);

        return employee;
    }

    public Employee updateAssignedProjectToEmployee(int employeeId, EmployeeDTO employeeDetails) {
        logger.info("update employee's assigned project method active inside UpdateEmployeeDAO");

        Employee updateEmployee = employeeRepository.findEmployeeById(employeeId);

        updateEmployee.setProject(new ProjectDAO(projectRepository).getProject(employeeDetails));

        return updateEmployee;
    }

    private boolean isFieldUpdated(String dbValue, String formData) {
        logger.info("checking if field is updated");

        if (formData == null || formData.isEmpty() || formData.isBlank()) return false;

        return !dbValue.trim().equalsIgnoreCase(formData.trim());
    }

}
