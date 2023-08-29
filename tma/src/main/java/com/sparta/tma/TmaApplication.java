package com.sparta.tma;

import com.sparta.tma.entities.*;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.JspTemplateAvailabilityProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class TmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmaApplication.class, args);
		System.out.println("Hello, World!");
	}

	@Transactional
	@Bean
	CommandLineRunner run(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder,
						  DepartmentRepository departmentRepository,
						  ProjectRepository projectRepository,
						  EmployeeRepository employeeRepository) {
		return args -> {
			if(appUserRepository.findByUsername("admin").isPresent()) return;

			String[] departments = {"HR", "Finance", "Marketing", "Sales", "Design", "Development", "Customer Service"};
			for (String d : departments) {
				Department department = new Department();
				department.setDepartment(d);
				departmentRepository.saveAndFlush(department);
			}

			String[] projects = {"Unassigned", "New Starters", "Attendance", "Payroll", "Accounts", "Products", "Advertising", "Web App Frontend", "Web App Backend", "Logo", "Web App"};
			for (String p : projects) {
				Project project = new Project();
				project.setProject(p);
				projectRepository.saveAndFlush(project);
			}


			Employee employee = new Employee();
			employee.setId(0);
			employee.setFirstName("Admin");
			employee.setLastName("Test");
			employee.setRole(Role.ADMIN);
			employee.setDepartment(departmentRepository.findByDepartmentIgnoreCase("hr"));
			employee.setProject(projectRepository.findById(1));
			Employee savedEmployee = employeeRepository.saveAndFlush(employee);

			AppUser admin = new AppUser("admin", passwordEncoder.encode("admin"), Role.ADMIN);
			admin.setEmployee(employeeRepository.findEmployeeById(savedEmployee.getId()));
			appUserRepository.save(admin);
		};
	}



}
