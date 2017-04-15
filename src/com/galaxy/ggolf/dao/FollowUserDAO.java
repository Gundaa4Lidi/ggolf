package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.FollowUserRowMapper;
import com.galaxy.ggolf.domain.FollowUser;

public class FollowUserDAO extends GenericDAO<FollowUser> {

	public FollowUserDAO() {
		super(new FollowUserRowMapper());
	}
	
	//根据关系获取好友列表
	public Collection<FollowUser> getFollow(String UserID,String Relation,String Relation1){
		String sql = "SELECT u.`UserID`, u.`Name`, u.`age`, u.`Sex`,u.`head_portrait`,f.Relation,f.Remark,f.Status,f.UID FROM "
				+ "`follow` f LEFT JOIN `user` u on u.UserID = f.FenID"
				+ " WHERE f.DeletedFlag is null and f.userID='"+UserID+"' and (f.Relation='"+Relation+"' or f.Relation='"+Relation1+"')";
		return super.executeQuery(sql);
	}
	

}
