package com.cognixia.jump.jdbc.dao.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognixia.jump.jdbc.dao.PayrollDAO;
import com.cognixia.jump.jdbc.datamule.Payroll;
import com.cognixia.jump.jdbc.dbtool.ConnectionManager;

public class PayrollMySQL implements PayrollDAO {

	@Override
	public Payroll getPayrollByEmployeeId(int id) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("SELECT * FROM payroll WHERE employee_id = ?");
			prpst.setInt(1, id);
			ResultSet rs = prpst.executeQuery();
			rs.next();
			Payroll payroll = new Payroll(rs.getInt("employee_id"),rs.getBoolean("is_salary"), rs.getDouble("rate"), rs.getBoolean("is_direct_deposit"), 
										rs.getString("account_number"), rs.getString("routing_number"));
			return payroll;
		} catch (SQLException e) {
			System.out.println("failed to retreive Payroll from DB");
		}
		return null;
	}

	@Override
	public boolean addPayroll(Payroll payroll) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("INSERT INTO payroll (employee_id, is_salary, rate, is_direct_deposit, account_number, "
																						+ "routing_number) VALUES (?,?,?,?,?,?)");
			prpst.setInt(1, payroll.getEmployee_id());
			prpst.setBoolean(2, payroll.isIs_salary());
			prpst.setDouble(3, payroll.getRate());
			prpst.setBoolean(4, payroll.isIs_direct_deposit());
			prpst.setString(5, payroll.getAccount());
			prpst.setString(6, payroll.getRouting());
			int affected = prpst.executeUpdate();
			return (affected > 0);
		} catch (SQLException e) {
			System.out.println("failed to Insert Payroll into DB");
		}
		return false;
	}

	@Override
	public boolean updatePayroll(Payroll payroll) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("UPDATE payroll SET is_salary = ?, rate = ?, is_direct_deposit = ?, account_number = '?',"
																						+ " routing_number = '?'  WHERE employee_id = ?");
			prpst.setBoolean(1, payroll.isIs_salary());
			prpst.setDouble(2, payroll.getRate());
			prpst.setBoolean(3, payroll.isIs_direct_deposit());
			prpst.setString(4, payroll.getAccount());
			prpst.setString(5, payroll.getRouting());
			prpst.setInt(6, payroll.getEmployee_id());
			int affected = prpst.executeUpdate();
			return (affected > 0);
		} catch (SQLException e) {
			System.out.println("failed to Update Payroll in DB");
		}
		return false;
	}

	@Override
	public boolean deletePayroll(Payroll payroll) {
		try {
			PreparedStatement prpst = ConnectionManager.getConnection().prepareStatement("DELETE FROM payroll WHERE employee_id = ?");
			prpst.setInt(1, payroll.getEmployee_id());
			int affected = prpst.executeUpdate();
			return (affected > 0);
		} catch (SQLException e) {
			System.out.println("failed to Delete Payroll from DB");
		}
		return false;
	}

}
