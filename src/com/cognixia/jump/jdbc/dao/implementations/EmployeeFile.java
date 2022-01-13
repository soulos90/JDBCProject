package com.cognixia.jump.jdbc.dao.implementations;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.jdbc.dao.EmployeeDAO;
import com.cognixia.jump.jdbc.datamule.Contact;
import com.cognixia.jump.jdbc.datamule.Employee;
import com.cognixia.jump.jdbc.datamule.Payroll;

public class EmployeeFile implements EmployeeDAO {
	private static File file = new File("resources/database.data");
	private static int id = 1;
	private int numEmployees = checkNumEmployees();
	private int checkNumEmployees() {
		int val = 0;
		try( ObjectInputStream reader = new ObjectInputStream( new FileInputStream(file) ) ) {
			val = reader.readInt();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(EOFException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return val;
	}
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		boolean cont = true;
		try( ObjectInputStream reader = new ObjectInputStream( new FileInputStream(file) ) ) {
			numEmployees = reader.readInt();
			for(int i = 0;i < numEmployees;++i) {
				Contact contact = (Contact) reader.readObject();
				Payroll payroll = (Payroll) reader.readObject();
				Employee employee = (Employee) reader.readObject();
				employee.setContact(contact);
				employee.setPayroll(payroll);
				employees.add(employee);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(EOFException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return employees;
	}

	@Override
	public Employee getEmployeeById(int id) {
		List<Employee> employees = getAllEmployees();
		Employee employee = null;
		for(int i = 0; i < employees.size();++i) {
			if(employees.get(i).getId() == id) {
				employee = employees.get(i);
				break;
			}
		}
		return employee;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		List<Employee> employees = getAllEmployees();
		for(int i = 0; i < employees.size();++i) {
			id = employees.get(i).getId();
		}
		++id;
		boolean succeed = false;
		employee.setId(id);
		employee.getContact().setEmployee_id(id);
		employee.getPayroll().setEmployee_id(id);
		++id;
		employees.add(employee);
		try(ObjectOutputStream writer = new ObjectOutputStream( new FileOutputStream(file,false) ) ) {
			writer.writeInt(++numEmployees);
			for(int i = 0; i < employees.size();++i) {
				writer.writeObject(employees.get(i).getContact());
				writer.writeObject(employees.get(i).getPayroll());
				writer.writeObject(employees.get(i));
			}
			succeed = true;
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return succeed;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		List<Employee> employees = getAllEmployees();
		for(int i = 0; i < employees.size();++i) {
			if(employees.get(i).getId() == employee.getId()) {
				employee.getContact().setEmployee_id(employee.getId());
				employee.getPayroll().setEmployee_id(employee.getId());
				employees.set(i, employee);
				break;
			}
		}
		boolean succeed = false;
		try(ObjectOutputStream writer = new ObjectOutputStream( new FileOutputStream(file,false) ) ) {
			writer.writeInt(numEmployees);
			for(int i = 0; i < employees.size();++i) {
				writer.writeObject(employees.get(i).getContact());
				writer.writeObject(employees.get(i).getPayroll());
				writer.writeObject(employees.get(i));
			}
			succeed = true;
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return succeed;
	}

	@Override
	public boolean deleteEmployee(Employee employee) {
		List<Employee> employees = getAllEmployees();
		for(int i = 0; i < employees.size();++i) {
			if(employees.get(i).getId() == employee.getId()) {
				employees.remove(i);
				break;
			}
		}
		boolean succeed = false;
		try(ObjectOutputStream writer = new ObjectOutputStream( new FileOutputStream(file,false) ) ) {
			writer.writeInt(numEmployees);
			for(int i = 0; i < employees.size();++i) {
				writer.writeObject(employees.get(i).getContact());
				writer.writeObject(employees.get(i).getPayroll());
				writer.writeObject(employees.get(i));
			}
			succeed = true;
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return succeed;
	}

}
