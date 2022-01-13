package com.cognixia.jump.jdbc.dbtool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static Connection connection = null;
	
	private static void makeConnection() {
		try {
			connection = DriverManager.getConnection(ConnectionResource.URL, ConnectionResource.USERNAME, ConnectionResource.PASSWORD);
		} catch (SQLException e) {
			System.out.println("could not make connection.");
		}
	}
	
	public static Connection getConnection() {
		if(connection == null) {
			makeConnection();
		}
		try {
			if(connection.isClosed()) {
				makeConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	public static void closeConnection() {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
