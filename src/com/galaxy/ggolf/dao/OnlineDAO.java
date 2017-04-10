package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.OnlineRowMapper;
import com.galaxy.ggolf.domain.Online;

public class OnlineDAO extends GenericDAO<Online> {

	public OnlineDAO() {
		super(new OnlineRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(String UserID){
		String sql = "insert into online(UserID,OnlineOrNot)values('"+UserID+"','Y')";
		return super.executeUpdate(sql);
	}
	
	public boolean online(String UserID){
		String sql = "update online set OnlineOrNot='Y' where UserID='"+UserID+"'";
		return super.executeUpdate(sql);
	}
	
	public boolean offline(String UserID){
		String sql = "update online set OnlineOrNot='N' where UserID='"+UserID+"'";
		return super.executeUpdate(sql);
	}
	
	public boolean addLocation(String UserID,String Location){
		String sql = "update online set LocationOrNot='"+Location+"' where UserID='"+UserID+"'";
		return super.executeUpdate(sql);
	}
	
	public boolean cleanLocation(String UserID){
		String sql = "update online set LocationOrNot='N' where UserID='"+UserID+"'";
		return super.executeUpdate(sql);
	}
	
	public Online getByUserID(String UserID){
		String sql = "select * from online where UserID='"+UserID+"'";
		Collection<Online> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Online) result.toArray()[0];
		}
		return null;
	}

}
