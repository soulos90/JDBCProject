package com.cognixia.jump.jdbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cognixia.jump.jdbc.dao.EmployeeDAO;
import com.cognixia.jump.jdbc.datamule.Contact;
import com.cognixia.jump.jdbc.datamule.Employee;
import com.cognixia.jump.jdbc.datamule.Payroll;

class ProjectRunnerTest {
	private static ProjectRunner runner;
	private static EmployeeDAO edao;
	private static int testCount;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		runner = new ProjectRunner();
		edao = runner.getEDAO();
		testCount = 0;
		System.out.println("Test initialized");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("finished tests. count: " + testCount);
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("running test");
	}

	@AfterEach
	void tearDown() throws Exception {
		testCount++;
	}

	@Test
	void testAdd() {
		assert(edao.addEmployee(new Employee("First", "Last", "123456789", "crime fighting", new Contact("123 There pl.", "a@mail.com", "12345678901"), 
						new Payroll(true, 4000.1, false, "", ""))));
	}
	
	@Test
	void testGetAll() {
		edao.addEmployee(new Employee("First", "Last", "123456789", "crime fighting", new Contact("123 There pl.", "a@mail.com", "12345678901"), 
						new Payroll(true, 4000.1, false, "", "")));
		List<Employee> employees = edao.getAllEmployees();
		assert(!employees.isEmpty());
	}
	
	@Test
	void testGetById() {
		edao.addEmployee(new Employee("First", "Last", "123456789", "crime fighting", new Contact("123 There pl.", "a@mail.com", "12345678901"), 
				new Payroll(true, 4000.1, false, "", "")));
		List<Employee> employees = edao.getAllEmployees();
		Employee employee = edao.getEmployeeById(employees.get(0).getId());
		assert(employee != null);
	}
	
	@Test
	void testDelete() {
		edao.addEmployee(new Employee("First", "Last", "123456789", "crime fighting", new Contact("123 There pl.", "a@mail.com", "12345678901"), 
				new Payroll(true, 4000.1, false, "", "")));
		List<Employee> employees = edao.getAllEmployees();
		Employee employee = edao.getEmployeeById(employees.get(0).getId());
		assert(edao.deleteEmployee(employee));
	}
	
	@Test
	void testUpdate() {
		edao.addEmployee(new Employee("First", "Last", "123456789", "crime fighting", new Contact("123 There pl.", "a@mail.com", "12345678901"), 
				new Payroll(true, 4000.1, false, "", "")));
		List<Employee> employees = edao.getAllEmployees();
		Employee employee = edao.getEmployeeById(employees.get(0).getId());
		employee.setFirst_name("newFirst");
		assert(edao.updateEmployee(employee));
	}
}
