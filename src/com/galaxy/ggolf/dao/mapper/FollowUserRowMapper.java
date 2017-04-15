package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.FollowUser;

public class FollowUserRowMapper implements RowMapper<FollowUser> {
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_Name = "Name";
	private static final String COLUMN_Age = "Age";
	private static final String COLUMN_Sex = "Sex";
	private static final String COLUMN_Head = "head_portrait";
	private static final String COLUMN_Relation = "Relation";
	private static final String COLUMN_Remark = "Remark";
	private static final String COLUMN_Status = "Status";
	private static final String COLUMN_UID = "UID";
	@Override
	public FollowUser mapRow(ResultSet res) throws SQLException {
		// TODO Auto-generated method stub
		String UserID = res.getString(COLUMN_UserID);
		String Name = res.getString(COLUMN_Name);
		String Age = res.getString(COLUMN_Age);
		String Sex = res.getString(COLUMN_Sex);
		String Head = res.getString(COLUMN_Head);
		String Relation = res.getString(COLUMN_Relation);
		String Remark = res.getString(COLUMN_Remark);
		String Status = res.getString(COLUMN_Status);
		String UID = res.getString(COLUMN_UID);
		return new FollowUser(UserID,Name,Age,Sex,Head,Relation,Remark,Status,UID);
	}

}
