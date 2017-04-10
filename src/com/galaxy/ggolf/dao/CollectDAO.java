package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CollectRowMapper;
import com.galaxy.ggolf.domain.Collect;

public class CollectDAO extends GenericDAO<Collect> {

	public CollectDAO() {
		super(new CollectRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(Collect col){
		String sql = "insert into collect(UserID, ThemeID, Type, Status, Created_TS)values(?,?,?,?,?)";
		return super.sqlUpdate(sql,col.getUserID(),col.getThemeID(),col.getType(),"1",Time());
	}
	
	public Collect getCollectByUserID(String UserID,String ThemeID,String Type){
		String sql = "select * from collect where UserID='"+UserID+"' and ThemeID='"+ThemeID+"' and Type='"+Type+"'";
		Collection<Collect> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Collect) result.toArray()[0];
		}
		return null;
 	}
	
	public boolean update(String UID,String Status){
		String sql = "update set collect Status='"+Status+"' where UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	public int getcount(String ThemeID,String Type){
		String sql = "select count(*) from collect where ThemeID='"+ThemeID+"' and Status='1' and Type='"+Type+"'";
		return super.count(sql);
	}
	
	public Collection<Collect> getUserList(String ThemeID,String Type){
		String sql = "select * from collect where ThemeID='"+ThemeID+"' and Status='1' and Type='"+Type+"'";
		return super.executeQuery(sql);
	}
}
