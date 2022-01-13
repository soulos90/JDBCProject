package com.cognixia.jump.jdbc.datamule;

import java.io.Serializable;

public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7156384773769213371L;
	private int employee_id;
	private String mail;
	private String email;
	private String phone;
	public Contact(String mail, String email, String phone) {
		super();
		this.mail = mail;
		this.email = email;
		this.phone = phone;
	}
	public Contact(int employee_id, String mail, String email, String phone) {
		super();
		this.employee_id = employee_id;
		this.mail = mail;
		this.email = email;
		this.phone = phone;
	}
	public Contact(int employee_id, Contact old) {
		super();
		this.employee_id = employee_id;
		this.mail = old.getMail();
		this.email = old.getEmail();
		this.phone = old.getPhone();
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Contact [employee_id=" + employee_id + ", mail=" + mail + ", email=" + email + ", phone=" + phone + "]";
	}
}
