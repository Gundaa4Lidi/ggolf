package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.UmengRowMapper;
import com.galaxy.ggolf.domain.Umeng;

/**
 * Created by Administrator on 2017-06-14.
 */
public class UmengDAO extends GenericDAO<Umeng>{

	public UmengDAO() {
		super(new UmengRowMapper());
		// TODO Auto-generated constructor stub
	}
   
	public boolean create(String UserID,String Umeng_Token){
		String sql = "insert into umeng (UserID,Umeng_Token,Created_TS)values(?,?,?)";
		return super.executeUpdate(sql,UserID,Umeng_Token,Time());
	}
	
	//根据用户编号修改token
	public boolean updateToken(String UserID,String Umeng_Token){
		String sql = "update umeng set Umeng_Token=?,Updated_TS=? where UserID=?";
		return super.executeUpdate(sql,Umeng_Token,Time(),UserID);
	}
	
	//根据token修改用户编号
	public boolean updateUserID(String UserID,String Umeng_Token){
		String sql = "update umeng set UserID=?,Updated_TS=? where Umeng_Token=?";
		return super.executeUpdate(sql,UserID,Time(),Umeng_Token);
	}
	
	public Collection<Umeng> getAll(){
		String sql = "select * from umeng group by Umeng_Token";
		return super.executeQuery(sql);
	}
	
	public Umeng getByUserID(String UserID){
		String sql = "select * from umeng where UserID='"+UserID+"'";
		Collection<Umeng> result = super.executeQuery(sql);
		if(result.size()>0){
			return (Umeng) result.toArray()[0];
		}
		return null;
	}
	
	public Umeng getByToken(String Umeng_Token){
		String sql = "select * from umeng where Umeng_Token='"+Umeng_Token+"'";
		Collection<Umeng> result = super.executeQuery(sql);
		if(result.size()>0){
			return (Umeng) result.toArray()[0];
		}
		return null;
	}
}
