package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.User;

public class UserRowMapper implements RowMapper<User> {

	public static final String COLUMN_UserID = "userID";
	public static final String COLUMN_Phone = "phone";
	public static final String COLUMN_Name = "name";
	public static final String COLUMN_Age = "age";
	public static final String COLUMN_Sex = "sex";
	public static final String COLUMN_Password = "password";
	public static final String COLUMN_Head_Portrait = "head_portrait";
	public static final String COLUMN_IsCoach = "isCoach";
	public static final String COLUMN_Longitude = "longitude";
	public static final String COLUMN_Latitude = "latitude";
	public static final String COLUMN_Wechat = "wechat";
	public static final String COLUMN_Created_TS = "Created_TS";
	public static final String COLUMN_Updated_TS = "Updated_TS";
	
	
	@Override
	public User mapRow(ResultSet res) throws SQLException {
		// TODO Auto-generated method stub
		String userID = res.getString(COLUMN_UserID);
		String phone = res.getString(COLUMN_Phone);
		String name = res.getString(COLUMN_Name);
		String age = res.getString(COLUMN_Age);
		String sex = res.getString(COLUMN_Sex);
		String password = res.getString(COLUMN_Password);
		String head_portrait = res.getString(COLUMN_Head_Portrait);
		String isCoach = res.getString(COLUMN_IsCoach);
		String longitude = res.getString(COLUMN_Longitude);
		String latitude = res.getString(COLUMN_Latitude);
		String wechat = res.getString(COLUMN_Wechat);
		String created_TS = res.getString(COLUMN_Created_TS);
		String updated_TS = res.getString(COLUMN_Updated_TS);
		return new User(userID, phone, name, age, sex, password, head_portrait,isCoach,longitude,latitude,
				wechat, created_TS, updated_TS);
	}

}
