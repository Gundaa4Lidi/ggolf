package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.UserLogRec;

public class UserLogRecRowMapper implements RowMapper<UserLogRec> {
	public static final String COLUMN_UserID = "UserID";
	public static final String COLUMN_Login_Place = "Login_Place";
	public static final String COLUMN_Created_TS = "Created_TS";
	public static final String COLUMN_Longitude = "Longitude";
	public static final String COLUMN_latitude = "latitude";
	
	@Override
	public UserLogRec mapRow(ResultSet res) throws SQLException {
	    String UserID = res.getString(COLUMN_UserID);
	    String Login_Place = res.getString(COLUMN_Login_Place);
	    String Created_TS = res.getString(COLUMN_Created_TS);
	    String Longitude = res.getString(COLUMN_Longitude);
	    String latitude = res.getString(COLUMN_latitude);
		return new UserLogRec(UserID,Login_Place,Created_TS,Longitude,latitude);
	}

}
