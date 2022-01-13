package com.cognixia.jump.jdbc.dao.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognixia.jump.jdbc.dao.ContactDAO;
import com.cognixia.jump.jdbc.datamule.Contact;
import com.cognixia.jump.jdbc.dbtool.ConnectionManager;

public class ContactMySQL implements ContactDAO {

	@Override
	public Contact getContactByEmployeeId(int id) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("SELECT * FROM contact WHERE employee_id = ?");
			prpst.setInt(1, id);
			ResultSet rs = prpst.executeQuery();
			rs.next();
			Contact contact = new Contact(rs.getInt("employee_id"),rs.getString("mail_address"), rs.getString("email_address"), rs.getString("phone_number"));
			return contact;
		} catch (SQLException e) {
			System.out.println("failed to retreive Contact from DB");
		}
		return null;
	}

	@Override
	public boolean addContact(Contact contact) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("INSERT INTO contact (employee_id, mail_address, email_address, phone_number) VALUES (?,?,?,?)");
			prpst.setInt(1, contact.getEmployee_id());
			prpst.setString(2, contact.getMail());
			prpst.setString(3, contact.getEmail());
			prpst.setString(4, contact.getPhone());
			int affected = prpst.executeUpdate();
			return (affected > 0);
		} catch (SQLException e) {
			System.out.println("failed to Insert Contact into DB");
		}
		return false;
	}

	@Override
	public boolean updateContact(Contact contact) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("UPDATE contact SET mail_address = ?, email_address = ?, phone_number = ?"
																							+ " WHERE employee_id = ?");
			prpst.setString(1, contact.getMail());
			prpst.setString(2, contact.getEmail());
			prpst.setString(3, contact.getPhone());
			prpst.setInt(4, contact.getEmployee_id());
			int affected = prpst.executeUpdate();
			return (affected > 0);
		} catch (SQLException e) {
			System.out.println("failed to Update Contact in DB");
		}
		return false;
	}

	@Override
	public boolean deleteContact(Contact contact) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("DELETE FROM contact WHERE employee_id = ?");
			prpst.setInt(1, contact.getEmployee_id());
			int affected = prpst.executeUpdate();
			return (affected > 0);
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("failed to Delete Contact from DB");
		}
		return false;
	}

}
