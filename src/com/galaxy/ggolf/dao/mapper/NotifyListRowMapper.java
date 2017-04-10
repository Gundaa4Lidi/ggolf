package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.NotifyList;

public class NotifyListRowMapper implements RowMapper<NotifyList> {
	private static final String COLUMN_NotifyID = "NotifyID";
	private static final String COLUMN_MsgID = "MsgID";
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_UserName = "UserName";
	private static final String COLUMN_HeadPhoto = "HeadPhoto";
	private static final String COLUMN_ReadOrNot = "ReadOrNot";
	
	@Override
	public NotifyList mapRow(ResultSet res) throws SQLException {
	    String NotifyID = res.getString(COLUMN_NotifyID);
	    String MsgID = res.getString(COLUMN_MsgID);
	    String UserID = res.getString(COLUMN_UserID);
	    String UserName = res.getString(COLUMN_UserName);
	    String HeadPhoto = res.getString(COLUMN_HeadPhoto); 
	    String ReadOrNot = res.getString(COLUMN_ReadOrNot);
		return new NotifyList(NotifyID,MsgID,UserID,UserName,HeadPhoto,ReadOrNot);
	}

}
