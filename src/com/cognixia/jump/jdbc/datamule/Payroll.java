package com.cognixia.jump.jdbc.datamule;

import java.io.Serializable;

public class Payroll implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4032360188026481469L;
	private int employee_id;
	private boolean is_salary;
	private double rate;
	private boolean is_direct_deposit;
	private String account;
	private String routing;
	public Payroll(boolean is_salary, double rate, boolean is_direct_deposit, String account, String routing) {
		super();
		this.is_salary = is_salary;
		this.rate = rate;
		this.is_direct_deposit = is_direct_deposit;
		this.account = account;
		this.routing = routing;
	}
	public Payroll(int employee_id, boolean is_salary, double rate, boolean is_direct_deposit, String account,
			String routing) {
		super();
		this.employee_id = employee_id;
		this.is_salary = is_salary;
		this.rate = rate;
		this.is_direct_deposit = is_direct_deposit;
		this.account = account;
		this.routing = routing;
	}
	public Payroll(int employee_id, Payroll old) {
		super();
		this.employee_id = employee_id;
		this.is_salary = old.isIs_salary();
		this.rate = old.getRate();
		this.is_direct_deposit = old.isIs_direct_deposit();
		this.account = old.getAccount();
		this.routing = old.getRouting();
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public boolean isIs_salary() {
		return is_salary;
	}
	public void setIs_salary(boolean is_salary) {
		this.is_salary = is_salary;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public boolean isIs_direct_deposit() {
		return is_direct_deposit;
	}
	public void setIs_direct_deposit(boolean is_direct_deposit) {
		this.is_direct_deposit = is_direct_deposit;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRouting() {
		return routing;
	}
	public void setRouting(String routing) {
		this.routing = routing;
	}
	@Override
	public String toString() {
		return "Payroll [employee_id=" + employee_id + ", is_salary=" + is_salary + ", rate=" + rate
				+ ", is_direct_deposit=" + is_direct_deposit + ", account=" + account + ", routing=" + routing + "]";
	}
}
