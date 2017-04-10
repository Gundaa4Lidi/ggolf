package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.RowMapper;
import com.galaxy.ggolf.dao.mapper.UserLogRecRowMapper;
import com.galaxy.ggolf.domain.UserLogRec;

public class UserLogRecDAO extends GenericDAO<UserLogRec> {

	public UserLogRecDAO() {
		super(new UserLogRecRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean createUserLogRec(UserLogRec userLogRec){
		String sql = "insert into user_login_record(UserID,Login_Place,Created_TS,Longitude,latitude)values(?,?,?,?,?)";
		return super.sqlUpdate(sql, userLogRec.getUserID(),userLogRec.getLogin_Place(),
						Time(),userLogRec.getLongitude(),userLogRec.getLatitude());
	}
	
	public Collection<UserLogRec> getAll(){
		String sql = "select * from user_login_record order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	public Collection<UserLogRec> getRecordByUserID(String userid){
		String sql = "select * from user_login_record where UserID = '"+userid+"' order by Created_TS desc";
		return super.executeQuery(sql);
	}

}
