package com.cognixia.jump.jdbc;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.jdbc.dao.EmployeeDAO;
import com.cognixia.jump.jdbc.dao.implementations.ContactMySQL;
import com.cognixia.jump.jdbc.dao.implementations.EmployeeFile;
import com.cognixia.jump.jdbc.dao.implementations.EmployeeMySQL;
import com.cognixia.jump.jdbc.dao.implementations.PayrollMySQL;
import com.cognixia.jump.jdbc.datamule.Contact;
import com.cognixia.jump.jdbc.datamule.Employee;
import com.cognixia.jump.jdbc.datamule.Payroll;
import com.cognixia.jump.jdbc.exception.OutOfMenuBoundsException;
import com.cognixia.jump.jdbc.exception.WrongLengthException;

public class ProjectRunner {

	private static EmployeeDAO EDAO = new EmployeeFile();
	Scanner sc;
	public static void main(String[] args) {
		ProjectRunner runner = new ProjectRunner();
	}
	public ProjectRunner() {
		loop();
	}
	private void loop() {
		sc = new Scanner(System.in);
		System.out.println("Welcome to the EMS Application");
		
		while(UI());
		
		sc.close();
	}
	public boolean UI(){
		boolean loopAgain = true;
		try {
			System.out.println("\nSelect an option:");
			System.out.println("1. View Employees \n2. Add Employee \n3. Delete Employee \n4. Edit Employee \n5. Select Storage method \n6. Exit");
			int choice = sc.nextInt();
			System.out.println("");
			switch(choice) {
			case 1:
				List<Employee> employees = EDAO.getAllEmployees();
				if(employees.isEmpty()) {
					System.out.println("There are no employees to view");
					return loopAgain;
				}
				employees.forEach(System.out :: println);
				break;
			case 2:
				addEmployee();
				break;
			case 3:
				deleteEmployee();
				break;
			case 4:
				editEmployee();
				break;
			case 5: 
				SelectStorage();
				break;
			case 6:
				loopAgain = false;
				break;
			default:
				throw new OutOfMenuBoundsException("that menu option doesn't exist. Please input number of selection.");
			}
		}
		catch (OutOfMenuBoundsException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(InputMismatchException e) {
			System.out.println("that menu option doesn't exist. Please input number of selection.");
			sc.next();
		}
		return loopAgain;
	}
	private void SelectStorage() {
		System.out.println("Select storage prefference\n1. MySQL\n2. File");
		try {
			int choice = sc.nextInt();
			System.out.println("");
			switch(choice) {
			case 1:
				EDAO = new EmployeeMySQL(new ContactMySQL(), new PayrollMySQL());
				break;
			case 2:
				EDAO = new EmployeeFile();
				break;
				default:
					throw new OutOfMenuBoundsException("that menu option doesn't exist. Please input number of selection.");
			}
		}catch (OutOfMenuBoundsException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(InputMismatchException e) {
			System.out.println("that menu option doesn't exist. Please input number of selection.");
			sc.next();
		}
	}
	private void addEmployee() {
		EDAO.addEmployee(gatherEmployeeData());
	}
	private void deleteEmployee() {
		List<Employee> employees = EDAO.getAllEmployees();
		if(employees.isEmpty()) {
			System.out.println("There are no employees to delete");
			return;
		}
		System.out.println("which employee would you like to delete?");
		for(int i = 0; i < employees.size(); ++i) {
			System.out.println(employees.get(i).getId() + ". " + employees.get(i).getFirst_name() + " " + employees.get(i).getLast_name());
		}
		boolean cont = false;
		do{
			try {
				int choice = sc.nextInt();
				EDAO.deleteEmployee(EDAO.getEmployeeById(choice));
				cont = true;
			}catch(InputMismatchException e) {
				System.out.println("please input an integer.");
				sc.next();
			}
		}while(!cont);
	}
	private void editEmployee() {
		List<Employee> employees = EDAO.getAllEmployees();
		if(employees.isEmpty()) {
			System.out.println("There are no employees to edit");
			return;
		}
		System.out.println("which employee would you like to edit?");
		for(int i = 0; i < employees.size(); ++i) {
			System.out.println(employees.get(i).getId() + ". " + employees.get(i).getFirst_name() + " " + employees.get(i).getLast_name());
		}
		boolean cont = false;
		do{
			try {
				int choice = sc.nextInt();
				EDAO.updateEmployee(new Employee(choice,gatherEmployeeData()));
				cont = true;
			}catch(InputMismatchException e) {
				System.out.println("please input an integer.");
				sc.next();
			}
		}while(!cont);
	}
	private Employee gatherEmployeeData() {
		String ssn = "", first_name, last_name, department;
		boolean salary = false, direct_deposit = false;
		double rate = 0;
		String account = "", routing = "";
		String address, email, phone = "";
		char temp;
		System.out.println("would you like to generate a quick employee? (Y/N) (skips data prompts)");
		temp = sc.next().charAt(0);
		if(temp == 'y' || temp == 'Y')
			return new Employee("Nathan","Raymond","123456789","IT", new Contact("123 Place", "e@mail.com", "12343454567"), new Payroll(true,4000,true,"1234567890","123456789"));
		System.out.println("What is employee's ssn? (9 characters)");
		boolean cont = false;
		do{
			try {
				ssn = sc.next();
				if(ssn.length() == 9) {
					cont = true;
					for(int i = 0; i < phone.length();++i) {
						if(!(phone.charAt(i) < '9' && phone.charAt(i) > '0')) {
							cont = false;
							throw new InputMismatchException("Please enter only numeric digits");
						}
					}
				}else {
					throw new WrongLengthException("please enter 9 digits");
				}
			}catch(WrongLengthException e) {
				System.out.println(e.getMessage());
			}catch(InputMismatchException e) {
				System.out.println(e.getMessage());
			}
		}while(!cont);
		System.out.println("What is employee's first name?");
		first_name = sc.next();
		System.out.println("What is employee's last name?");
		last_name = sc.next();
		System.out.println("What is employee's department?");
		department = sc.next();
		System.out.println("Is the employee salary? (Y/N)");
		temp = sc.next().charAt(0);
		if(temp == 'y' || temp == 'Y') {
			salary = true;
			System.out.println("What is employee's bi-weekly rate?");
			cont = false;
			do{
				try {
					rate = sc.nextDouble();
					cont = true;
				}catch(InputMismatchException e) {
					System.out.println("please input an real number.");
					sc.next();
				}
			}while(!cont);
		}else {
			System.out.println("What is employee's hourly rate?");
			cont = false;
			do{
				try {
					rate = sc.nextDouble();
					cont = true;
				}catch(InputMismatchException e) {
					System.out.println("please input an real number.");
					sc.next();
				}
			}while(!cont);
		}
		System.out.println("Is the employee direct deposit? (Y/N)");
		temp = sc.next().charAt(0);
		if(temp == 'y' || temp == 'Y') {
			direct_deposit = true;
			System.out.println("What is employee's bank account number? (10 characters)");
			cont = false;
			do{
				try {
					account = sc.next();
					if(account.length() == 10) {
						cont = true;	
						for(int i = 0; i < phone.length();++i) {
							if(!(phone.charAt(i) < '9' && phone.charAt(i) > '0')) {
								cont = false;
								throw new InputMismatchException("Please enter only numeric digits");
							}
						}
					}else {
						throw new WrongLengthException("please enter 10 digits");
					}
				}catch(WrongLengthException e) {
					System.out.println(e.getMessage());
				}catch(InputMismatchException e) {
					System.out.println(e.getMessage());
				}
			}while(!cont);
			System.out.println("What is employee's bank routing number? (9 characters)");
			cont = false;
			do{
				try {
					routing = sc.next();
					if(routing.length() == 9) {
						cont = true;	
						for(int i = 0; i < phone.length();++i) {
							if(!(phone.charAt(i) < '9' && phone.charAt(i) > '0')) {
								cont = false;
								throw new InputMismatchException("Please enter only numeric digits");
							}
						}
					}else {
						throw new WrongLengthException("please enter 9 digits");
					}
				}catch(WrongLengthException e) {
					System.out.println(e.getMessage());
				}catch(InputMismatchException e) {
					System.out.println(e.getMessage());
				}
			}while(!cont);
		}
		System.out.println("What is employee's mailing address?");
		address = sc.next();
		System.out.println("What is employee's email address?");
		email = sc.next();
		System.out.println("What is employee's phone number? (11 characters)");
		cont = false;
		do{
			try {
				phone = sc.next();
				if(phone.length() == 9) {
					cont = true;	
					for(int i = 0; i < phone.length();++i) {
						if(!(phone.charAt(i) < '9' && phone.charAt(i) > '0')) {
							cont = false;
							throw new InputMismatchException("Please enter only numeric digits");
						}
					}
				}else {
					throw new WrongLengthException("please enter 11 digits");
				}
			}catch(WrongLengthException e) {
				System.out.println(e.getMessage());
			}catch(InputMismatchException e) {
				System.out.println(e.getMessage());
			}
		}while(!cont);
		return new Employee(first_name, last_name, ssn, department, new Contact(address, email, phone), new Payroll(salary,rate,direct_deposit,account,routing));
	}
}
