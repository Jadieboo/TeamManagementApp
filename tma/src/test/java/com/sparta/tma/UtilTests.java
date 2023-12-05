package com.sparta.tma;

import com.sparta.tma.entities.Department;
import com.sparta.tma.entities.Project;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.ProjectRepository;
import com.sparta.tma.utils.PopulateEmployeeAttributes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UtilTests {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PopulateEmployeeAttributes popUtil;

    @Test
    public void populateListOfDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        List<String> expected = new ArrayList<>();

        List<String> results = popUtil.populateDepartmentOptions();

        for (Department d : departmentList) {
            expected.add(d.getDepartment());
        }

        assertEquals(expected, results);
    }

    @Test
    public void populateListOfProjects() {
        List<Project> projectList = projectRepository.findAll();
        List<String> expected = new ArrayList<>();

        List<String> results = popUtil.populateProjectOptions();

        for (Project p : projectList) {
            expected.add(p.getProject());
        }

        assertEquals(expected, results);
    }

    @Test
    public void populateListOfRoles() {
        String[] roles = {"Admin", "Manager", "Employee"};

        List<String> expected = new ArrayList<>(List.of(roles));
        List<String> results = popUtil.populateRoleOptions();
        
        assertEquals(expected, results);
    }
}
