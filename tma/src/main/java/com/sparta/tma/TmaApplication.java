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

import java.io.InputStream;
import java.util.Scanner;

@SpringBootApplication
public class TmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmaApplication.class, args);
		System.out.println("*** App Running ***");


//		System.out.println("** 100 days of code **");

		//TODO :
		// Day 7 Math Operations
		// Write a program that performs the following tasks using Math class:
		// Maximum of 2 numbers - DONE
		// Minimum of 2 numbers - DONE
		// Square root of a number - DONE
		// Floor and Ceil of a float - DONE
		// Abs value of a double

		System.out.println(max(50,30));
		System.out.println(min(50,30));
		System.out.println(sqrt(49));
		System.out.println(floorCeil(100.45f, "floor"));
		System.out.println(floorCeil(100.45f, "ceil"));


	}

	public static int max(int num1, int num2) {
		return Math.max(num1, num2);
	}

	public static int min(int num1, int num2) {
		return Math.min(num1, num2);
	}

	public static double sqrt(int num1) {
		return Math.sqrt(num1);
	}

	public static double floorCeil(float num1, String type) {
		double outcome = 0;
		switch (type) {
			case "floor" -> {
				outcome = Math.floor(num1);
			}
			case "ceil" -> {
				outcome = Math.ceil(num1);
			}
		}

		return outcome;
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
