package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Staff;

public class StaffRowMapper implements RowMapper<Staff> {
	public static final String COLUMN_UID = "UID";
	public static final String COLUMN_StaffName = "StaffName";
	public static final String COLUMN_Phone = "Phone";
	public static final String COLUMN_StaffID = "StaffID";
	public static final String COLUMN_Password = "Password";
	public static final String COLUMN_Head = "Head";
	public static final String COLUMN_Created_TS = "Created_TS";
	public static final String COLUMN_Updated_TS = "Updated_TS";
	public static final String COLUMN_Status = "Status";
	public static final String COLUMN_Position = "Position";
	public static final String COLUMN_WorkPlace = "WorkPlace";

	@Override
	public Staff mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String StaffName = res.getString(COLUMN_StaffName);
		String Phone = res.getString(COLUMN_Phone);
		String StaffID = res.getString(COLUMN_StaffID);
		String Password = res.getString(COLUMN_Password);
		String Head = res.getString(COLUMN_Head);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		String Status = res.getString(COLUMN_Status);
		String Position = res.getString(COLUMN_Position);
		String WorkPlace = res.getString(COLUMN_WorkPlace);
		return new Staff(UID,StaffName,Phone,StaffID,Password,Head,Created_TS,Updated_TS,Status,Position,WorkPlace);
	}

}
