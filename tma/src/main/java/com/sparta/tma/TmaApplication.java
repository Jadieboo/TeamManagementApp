package com.sparta.tma;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Employee;
import com.sparta.tma.entities.Role;
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
			if(appUserRepository.findByUsername("admin").isPresent()) return;

			Employee employee = new Employee();
			employee.setId(0);
			employee.setFirstName("admin");
			employee.setLastName("test");
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
