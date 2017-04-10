package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Online;

public class OnlineRowMapper implements RowMapper<Online>{
	
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_OnlineOrNot = "OnlineOrNot";
	private static final String COLUMN_LocationOrNot = "LocationOrNot";

	@Override
	public Online mapRow(ResultSet res) throws SQLException {
		String UserID = res.getString(COLUMN_UserID);
		String OnlineOrNot = res.getString(COLUMN_OnlineOrNot); 
		String LocationOrNot = res.getString(COLUMN_LocationOrNot);
		return new Online(UserID,OnlineOrNot,LocationOrNot);
	}
	
}
