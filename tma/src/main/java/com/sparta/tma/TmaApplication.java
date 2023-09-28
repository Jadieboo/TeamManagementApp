package com.sparta.tma;

import com.sparta.tma.entities.*;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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
			if (appUserRepository.findByUsername("admin").isPresent()) return;

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

			Employee employeeAdmin = new Employee();
			employeeAdmin.setId(0);
			employeeAdmin.setFirstName("Admin");
			employeeAdmin.setLastName("Test");
			employeeAdmin.setRole(Role.ADMIN);
			employeeAdmin.setDepartment(departmentRepository.findByDepartmentIgnoreCase("hr"));
			employeeAdmin.setProject(projectRepository.findById(1));
			Employee employeeAdminSaved = employeeRepository.saveAndFlush(employeeAdmin);

			AppUser admin = new AppUser("admin", passwordEncoder.encode("admin"), Role.ADMIN);
			admin.setEmployee(employeeRepository.findEmployeeById(employeeAdminSaved.getId()));
			appUserRepository.save(admin);

			Employee employeeManager = new Employee();
			employeeManager.setId(0);
			employeeManager.setFirstName("Manager");
			employeeManager.setLastName("Test");
			employeeManager.setRole(Role.MANAGER);
			employeeManager.setDepartment(departmentRepository.findByDepartmentIgnoreCase("hr"));
			employeeManager.setProject(projectRepository.findById(1));
			Employee employeeManagerSaved = employeeRepository.saveAndFlush(employeeManager);

			AppUser manager = new AppUser("manager", passwordEncoder.encode("manager"), Role.MANAGER);
			manager.setEmployee(employeeRepository.findEmployeeById(employeeManagerSaved.getId()));
			appUserRepository.save(manager);

			Employee employeeEmployee = new Employee();
			employeeEmployee.setId(0);
			employeeEmployee.setFirstName("Employee");
			employeeEmployee.setLastName("Test");
			employeeEmployee.setRole(Role.EMPLOYEE);
			employeeEmployee.setDepartment(departmentRepository.findByDepartmentIgnoreCase("hr"));
			employeeEmployee.setProject(projectRepository.findById(1));
			Employee savedEmployee = employeeRepository.saveAndFlush(employeeEmployee);

			AppUser employee = new AppUser("employee", passwordEncoder.encode("employee"), Role.EMPLOYEE);
			employee.setEmployee(employeeRepository.findEmployeeById(savedEmployee.getId()));
			appUserRepository.save(employee);
		};
	}



}
