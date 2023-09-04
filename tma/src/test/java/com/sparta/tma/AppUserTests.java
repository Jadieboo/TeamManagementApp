package com.sparta.tma;

import com.sparta.tma.DAOs.AppUserDAO;
import com.sparta.tma.DAOs.EmployeeDAO;
import com.sparta.tma.DTOs.EmployeeDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Role;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppUserTests {

    TestUtils utils = new TestUtils();
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PasswordEncoder encoder;

    @Test
    @DisplayName("Given all employeeDTO fields are present -> Creates a new AppUser using createNewAppUser() method to set " +
            "id, username, password, role, employee ")
    public void createNewAppUser() {

        EmployeeDTO employeeDetails = new EmployeeDTO();
        employeeDetails.setId(1);
        employeeDetails.setFirstName("admin");
        employeeDetails.setLastName("test");
        employeeDetails.setRole("ADMIN");
        employeeDetails.setDepartment("hr");
        employeeDetails.setProject("unassigned");

        Employee employee = new EmployeeDAO(departmentRepository, projectRepository).createNewEmployee(employeeDetails);
        employee.setId(1);

        AppUser user = new AppUserDAO(encoder, employeeRepository).createNewAppUser(employeeDetails, employee.getId());

        String expected = "AppUser{id: 0, username: Atest1@company.com, password: " + user.getPassword() + ", role: ADMIN, employee: " + employee + "}";

        assertEquals(expected, user.toString());
    }

    @Test
    @DisplayName("Return true if a new AppUser object matches an existing AppUser object")
    public void newAppUserObjectTypeMatches() {
        AppUser newUser = new AppUser();
        newUser.setId(1L);
        newUser.setUsername("admin");
        newUser.setPassword(encoder.encode("admin"));
        newUser.setRole(Role.ADMIN);
        newUser.setEmployee(employeeRepository.findEmployeeById(1));

        AppUser user = appUserRepository.findById(1);

        assertSame(newUser.getClass(), user.getClass());
    }

    @Test
    @DisplayName("Given a password is encoded, return true if it matches string raw password")
    public void passwordEncryptionMatchesString() {
        String password = "youDontKn0w!!";

        AppUser user = new AppUser("username", encoder.encode(password), Role.MANAGER);
        user.setEmployee(employeeRepository.findEmployeeById(1));

        assertTrue(encoder.matches(password, user.getPassword()));
    }

    @Test
    @DisplayName("Given an employee has a role of admin, user authority should be admin")
    public void adminAuthority() {
        AppUser user = appUserRepository.findById(1);

        Collection<? extends GrantedAuthority> authorityResult = user.getAuthorities();

        List<String> expected = Collections.singletonList(Role.ADMIN.name());

        assertEquals(expected.toString(), authorityResult.toString());
    }

    @Test
    @DisplayName("Given an employee has a role of manager, user authority should be manager")
    public void mangerAuthority() {
        AppUser user = appUserRepository.findById(1);
        user.setRole(Role.MANAGER);

        Collection<? extends GrantedAuthority> authorityResult = user.getAuthorities();

        List<String> expected = Collections.singletonList(Role.MANAGER.name());

        assertEquals(expected.toString(), authorityResult.toString());
    }

    @Test
    @DisplayName("Given an employee has a role of employee, user authority should be employee")
    public void employeeAuthority() {
        AppUser user = appUserRepository.findById(1);
        user.setRole(Role.EMPLOYEE);

        Collection<? extends GrantedAuthority> authorityResult = user.getAuthorities();

        List<String> expected = Collections.singletonList(Role.EMPLOYEE.name());

        assertEquals(expected.toString(), authorityResult.toString());
    }



}
