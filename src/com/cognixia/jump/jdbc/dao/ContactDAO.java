package com.cognixia.jump.jdbc.dao;

import com.cognixia.jump.jdbc.datamule.Contact;

public interface ContactDAO {
	public Contact getContactByEmployeeId(int id);
	
	public boolean addContact(Contact contact);
	public boolean updateContact(Contact contact);
	public boolean deleteContact(Contact contact);
}
