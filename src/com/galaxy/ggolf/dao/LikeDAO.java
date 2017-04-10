package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.LikeRowMapper;
import com.galaxy.ggolf.domain.Likes;

public class LikeDAO extends GenericDAO<Likes> {

	public LikeDAO() {
		super(new LikeRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(Likes like){
		String sql = "insert into likes(UserID, ThemeID, Type, Status, Created_TS)values(?,?,?,?,?)";
		return super.sqlUpdate(sql,like.getUserID(),like.getThemeID(),like.getType(),"1",Time());
	}
	
	public Likes getLikeByUserID(String UserID,String ThemeID,String Type){
		String sql = "select * from likes where UserID='"+UserID+"' and ThemeID='"+ThemeID+"' and Type='"+Type+"'";
		Collection<Likes> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Likes) result.toArray()[0];
		}
		return null;
 	}
	
	public Collection<Likes> getByThemeID(String ThemeID,String Type){
		String sql = "select * from likes where ThemeID='"+ThemeID+"' and Type='"+Type+"' ";
		return super.executeQuery(sql);
	}
	
	public int getCountByThemeID(String ThemeID,String Type){
		String sql = "select count(*) from likes where ThemeID='"+ThemeID+"' and Type='"+Type+"' ";
		return super.count(sql);
	}
}
