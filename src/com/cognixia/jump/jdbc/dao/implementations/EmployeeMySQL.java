package com.cognixia.jump.jdbc.dao.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.jdbc.dao.ContactDAO;
import com.cognixia.jump.jdbc.dao.EmployeeDAO;
import com.cognixia.jump.jdbc.dao.PayrollDAO;
import com.cognixia.jump.jdbc.datamule.Contact;
import com.cognixia.jump.jdbc.datamule.Employee;
import com.cognixia.jump.jdbc.datamule.Payroll;
import com.cognixia.jump.jdbc.dbtool.ConnectionManager;

public class EmployeeMySQL implements EmployeeDAO {
	private ContactDAO contact;
	private PayrollDAO payroll;
	public EmployeeMySQL(ContactDAO contact, PayrollDAO payroll) {
		this.contact = contact;
		this.payroll = payroll;
	}
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			Statement statement = ConnectionManager.getConnection().createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM employee");
			while(rs.next()) {
				int id = rs.getInt("id");
				employees.add(new Employee(id, rs.getString("first_name"), rs.getString("last_name"), rs.getString("ssn"), rs.getString("department_name"),
						contact.getContactByEmployeeId(id),payroll.getPayrollByEmployeeId(id)));
			}
			
		} catch (SQLException e) {
			System.out.println("failed to retreive Employees from DB");
		}
		return employees;
	}

	@Override
	public Employee getEmployeeById(int id) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("SELECT * FROM employee WHERE id = ?");
			prpst.setInt(1, id);
			ResultSet rs = prpst.executeQuery();
			rs.next();
			Employee employee = new Employee(id, rs.getString("first_name"), rs.getString("last_name"), rs.getString("ssn"), rs.getString("department_name"),
					contact.getContactByEmployeeId(id),payroll.getPayrollByEmployeeId(id));
			return employee;
		} catch (SQLException e) {
			System.out.println("failed to retreive Employee from DB");
		}
		return null;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("INSERT INTO employee (ssn, first_name, last_name, department_name) VALUES (?,?,?,?)",
																						PreparedStatement.RETURN_GENERATED_KEYS);
			prpst.setString(1, employee.getSsn());
			prpst.setString(2, employee.getFirst_name());
			prpst.setString(3, employee.getLast_name());
			prpst.setString(4, employee.getDepartment());
			int affected = prpst.executeUpdate();
			ResultSet keys = prpst.getGeneratedKeys();
			keys.next();
			int id = keys.getInt(1);
			if(affected > 0) {
				Contact newContact = new Contact(id, employee.getContact());
				Payroll newPayroll = new Payroll(id, employee.getPayroll());
				return (contact.addContact(newContact) && payroll.addPayroll(newPayroll));
			}
		} catch (SQLException e) {
			System.out.println("failed to Insert Employee into DB");
		}
		return false;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("UPDATE employee SET ssn = ?, first_name = ?, last_name = ?, "
																							+ "department_name = ? WHERE id = ?");
			prpst.setString(1, employee.getSsn());
			prpst.setString(2, employee.getFirst_name());
			prpst.setString(3, employee.getLast_name());
			prpst.setString(4, employee.getDepartment());
			prpst.setInt(5, employee.getId());
			int affected = prpst.executeUpdate();
			return (affected > 0 && contact.updateContact(employee.getContact()) && payroll.updatePayroll(employee.getPayroll()));
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("failed to Update Employee in DB");
		}
		return false;
	}

	@Override
	public boolean deleteEmployee(Employee employee) {
		try {
			if(contact.deleteContact(employee.getContact()) && payroll.deletePayroll(employee.getPayroll())) {
				PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("DELETE FROM employee WHERE id = ?");
				prpst.setInt(1, employee.getId());
				int affected = prpst.executeUpdate();
				return (affected > 0);
			}
		} catch (SQLException e) {
			System.out.println("failed to Delete Employee from DB");
		}
		return false;
	}

}
