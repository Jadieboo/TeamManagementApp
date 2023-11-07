package com.sparta.tma;

import com.sparta.tma.daos.EmployeeDAO;
import com.sparta.tma.daos.ProjectDAO;
import com.sparta.tma.dtos.EmployeeDTO;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Project;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProjectTests {

    Logger logger = LoggerFactory.getLogger(getClass());
    TestUtils utils = new TestUtils();
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;



    @ParameterizedTest
    @DisplayName("ProjectDAO.getProject() method returns a project regardless of the input being uppercase, lowercase, mixed case or has any whitespaces")
    @CsvSource({
            "Unassigned, Unassigned",
            "new starters, New Starters",
            "LOGO, Logo",
            "aTtEndANCE, Attendance",
            " Web App Frontend, Web App Frontend",
            "web app backend , Web App Backend",
            " Accounts , Accounts"
    })
    public void setOrUpdateEmployeeProject(String project, String expectedProject) {
        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(utils.employeeDetails());

        logger.info("Unit Test employee information before update: {}", employee);

        EmployeeDTO updatedProject = new EmployeeDTO();
        updatedProject.setProject(project);

        employee.setProject(new ProjectDAO(projectRepository).getProject(updatedProject));

        logger.info("Unit Test employee information after update: {}", employee);

        String result = employee.getProject().toString();

        assertEquals(expectedProject, result);
    }

    @ParameterizedTest
    @CsvSource({
            "un-assigned, un-assigned",
            "projekt, projekt",
            "New  Starters, new  starters",
            "Random Text, random text",
            "!, !",
            "123, 123",
            "., .",
            "<, <",
            "acb123, acb123"
    })
    @DisplayName("ProjectDAO.getProject() method handles if an illegal argument is passed, returns an IllegalArgumentException message: Could not find project called \"field input\" in database")
    public void ProjectDAOMethod_IllegalArgumentInput_ReturnsIllegalArgumentExceptionMessage(String fieldInput, String lowercaseText) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setProject(fieldInput);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new ProjectDAO(projectRepository).getProject(employeeDetails));

        String expected = "Could not find project called \"" + lowercaseText + "\" in database";

        assertEquals(expected, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("ProjectDAO.getProject() method handles if an empty/blank string is passed, returns default project: Unassigned")
    public void projectDAOMethod_BlankInput_ReturnsDefaultProject_Unassigned(String projectField) {
        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setProject(projectField);

        Project result = new ProjectDAO(projectRepository).getProject(employeeDetails);

        Project expected = projectRepository.findById(1);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("ProjectDAO.getProject() method handles if a null is passed, returns default project: Unassigned")
    public void projectDAOMethod_NullInput_ReturnsDefaultProject_Unassigned() {
        EmployeeDTO emptyEmployeeDTO = new EmployeeDTO();

        Project result = new ProjectDAO(projectRepository).getProject(emptyEmployeeDTO);

        Project expected = projectRepository.findById(1);

        assertEquals(expected, result);
    }


}
