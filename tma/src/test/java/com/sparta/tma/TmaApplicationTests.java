package com.sparta.tma;

import com.sparta.tma.entities.Employee;
import com.sparta.tma.repositories.DepartmentRepository;
import com.sparta.tma.repositories.EmployeeRepository;
import com.sparta.tma.repositories.ProjectRepository;
import com.sparta.tma.repositories.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TmaApplicationTests {
	@Autowired
	private EmployeeRepository eRepo;
	@Autowired
	private RoleRepository rRepo;
	@Autowired
	private DepartmentRepository dRepo;
	@Autowired
	private ProjectRepository pRepo;

	TestUtils ut = new TestUtils();



	@BeforeEach
	void createEmployee() {
		// create employee obj
		Employee createEmployee = new Employee();
		createEmployee.setId(0);
		createEmployee.setFirstName("Tess");
		createEmployee.setLastName("Ting");
		createEmployee.setRole(rRepo.findRoleByRoleIgnoreCase("employee"));
		createEmployee.setDepartment(dRepo.findById(5));
		createEmployee.setProject(pRepo.findById(10));

		// saves obj to database and allows me to access record and get actual id, not 0 as set above
		Employee newEmployee = eRepo.save(createEmployee);

		ut.setTestId(newEmployee.getId());

	}

	@AfterEach
	void deleteEmployee() {
		eRepo.deleteById(ut.getTestId());
	}


	@Test
	void getEmployee() {
		int id = ut.getTestId();
		String expected = String.format("Employee{id: %s, firstName: Tess, lastName: Ting, role: Employee, department: Design, project: Logo}", id);
		Employee result = eRepo.findEmployeeById(id);

		System.out.println(result.toString());
		assertEquals(expected, result.toString());
	}

}
