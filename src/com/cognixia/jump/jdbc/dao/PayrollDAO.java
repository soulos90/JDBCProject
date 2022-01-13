package com.cognixia.jump.jdbc.dao;

import com.cognixia.jump.jdbc.datamule.Payroll;

public interface PayrollDAO {
	public Payroll getPayrollByEmployeeId(int id);
	
	public boolean addPayroll(Payroll payroll);
	public boolean updatePayroll(Payroll payroll);
	public boolean deletePayroll(Payroll payroll);
}
