package com.cognixia.jump.jdbc.datamule;

import java.io.Serializable;

public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4595527040888279406L;
	private int id;
	private String first_name;
	private String last_name;
	private String ssn;
	private String department;
	private Contact contact = null;
	private Payroll payroll = null;
	public Employee(String first_name, String last_name, String ssn, String department, Contact contact, Payroll payroll) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.ssn = ssn;
		this.department = department;
		this.contact = contact;
		this.payroll = payroll;
	}
	public Employee(int id, String first_name, String last_name, String ssn, String department,
			Contact contact, Payroll payroll) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.ssn = ssn;
		this.department = department;
		this.contact = contact;
		this.payroll = payroll;
	}
	public Employee(int id, Employee old) {
		super();
		this.id = id;
		this.first_name = old.getFirst_name();
		this.last_name = old.getLast_name();
		this.ssn = old.getSsn();
		this.department = old.getDepartment();
		this.contact = old.getContact();
		this.payroll = old.getPayroll();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Payroll getPayroll() {
		return payroll;
	}
	public void setPayroll(Payroll payroll) {
		this.payroll = payroll;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", ssn=" + ssn + ", department="
				+ department + ", contact=" + contact + ", payroll=" + payroll + "]";
	}
}
