package com.cognixia.jump.jdbc.dao;

import java.util.List;

import com.cognixia.jump.jdbc.datamule.Employee;

public interface EmployeeDAO {
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(int id);
	
	public boolean addEmployee(Employee employee);
	public boolean updateEmployee(Employee employee);
	public boolean deleteEmployee(Employee employee);
}
