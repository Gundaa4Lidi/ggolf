package com.galaxy.ggolf.dao;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.galaxy.ggolf.dao.mapper.StaffRowMapper;
import com.galaxy.ggolf.domain.Staff;
import com.galaxy.ggolf.tools.CipherUtil;

public class StaffDAO extends GenericDAO<Staff> {

	public StaffDAO() {
		super(new StaffRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public int getCount() {
		String sql = "select count(*) from staff WHERE DeletedFlag is NULL";
		return super.count(sql);
	}
	
	public Collection<Staff> getAllStaff(){
		String sql = "select * from staff where DeletedFlag is NULL order by Created_TS desc";
		return super.executeQuery(sql);
	
	}
	
	public boolean saveHead(Staff staff){
		String sql = "update staff set Head='"+staff.getHead()+"',Updated_TS='"+Time()+"' where DeletedFlag is null and StaffID='"+staff.getStaffID()+"'";
		return super.executeUpdate(sql);
	}
	
	
	public Collection<Staff> addStaff(String StaffName){
		String sql = "select * from staff where DeletedFlag is NULL and StaffName = '"+StaffName+"'";
		return super.executeQuery(sql);
	}

	public boolean createStaff(Staff staff) {
		Date now = new Date();
		String dt = new String(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		CipherUtil cipher = new CipherUtil();
		String sql = "INSERT INTO staff (WorkPlace,StaffName,Phone,StaffID,Head,Password,Created_TS,Position) VALUES ('"
				+ staff.getWorkPlace()
				+ "','"
				+ staff.getStaffName()
				+ "','"
				+ staff.getPhone()
				+ "','"
				+ staff.getStaffID()
				+ "','"
				+ staff.getHead()
				+ "','"
				+ cipher.generatePassword(staff.getPassword())
				+ "','"
				+ dt
				+ "','" + staff.getPosition() + "')";
		return super.executeUpdate(sql);
	}
	
	public boolean updateStaff(Staff staff) {
		Date now = new Date();
		String dt = new String(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));		
		String sql = "update staff set WorkPlace='"+staff.getWorkPlace()
				+"', StaffName='"+staff.getStaffName()
				+"', Phone='"+staff.getPhone()
				+"', StaffID='"+staff.getStaffID()+"', Password='"+staff.getPassword()
				+"', Updated_TS='"+dt
				+"', Position='"+staff.getPosition()
				+"', Head='"+staff.getHead()
				+"' where UID = '"+staff.getUID()+"'";
		return super.executeUpdate(sql);
	}

	public boolean updateStaffPassword(String staffId,String password) {
		Date now = new Date();
		String dt = new String(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		CipherUtil cipher = new CipherUtil();
		System.out.print(cipher.generatePassword(password));
		String sql = "update staff set Password='"+cipher.generatePassword(password)
				+"', Updated_TS='"+dt
				+"' where StaffID = '"+staffId+"'";
		return super.executeUpdate(sql);
	}

	
	public Staff getStaffByStaffID(String StaffID) {
		String sql = "select * from staff where StaffID ='" + StaffID + "' and DeletedFlag is NULL";
		Collection<Staff> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return (Staff) result.toArray()[0];
		} else {
			return null;
		}
	}
	
	public Staff getadminStaffByStaffID(String StaffID) {
		String sql = "select * from staff where StaffID ='" + StaffID + "' and ( Position ='店员'or Position ='管理员' )and DeletedFlag is NULL ";
		Collection<Staff> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return (Staff) result.toArray()[0];
		} else {
			return null;
		}
	}

	public boolean updatebusyStatus(String StaffID) throws Exception {
		String sql = "update staff set Status ='busy' where StaffID ='"
				+ StaffID + "'";
		return super.executeUpdate(sql);
	}

	public boolean updatefreeStatus(String StaffID) throws Exception {
		String sql = "update staff set Status = 'free' where StaffID ='"
				+ StaffID + "'";
		return super.executeUpdate(sql);
	}

	public boolean deletStaff(String staffId) throws Exception {
		Date now = new Date();
		String dt = new String(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		String sql = "update staff set DeletedFlag ='Y',Updated_TS = '"+dt+"' where StaffID ='"
				+ staffId + "' ";
		return super.executeUpdate(sql);
	}
}
