package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.FollowRowMapper;
import com.galaxy.ggolf.domain.Follow;

public class FollowDAO extends GenericDAO<Follow> {

	public FollowDAO() {
		super(new FollowRowMapper());
	}
	
	public boolean create(Follow fol){//创建
		String sql = "insert into follow(UserID,FenID,Relation,Status,Created_TS)values(?,?,?,?)";
		return super.sqlUpdate(sql, fol.getUserID(),fol.getFenID(),fol.getRelation(),fol.getStatus(),Time());
	}
	
	//修改关系
	public boolean update(String relation, String status, String uid){
		String sql = "update follow set Relation='"+relation+"',Status='"+status+"' where UID='"+uid+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
	public Collection<Follow> getByUserID(String userID){//根据用户编号查询关注的人
		String sql = "select * from follow where DeletedFlag is null and UserID = '"+userID+"' and Relation !='黑名单' order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	public int getFollowCount(String userID){//查看用户关注多少人
		String sql = "select count(*) from follow where UserID='"+userID+"' and DeletedFlag is null and Relation !='黑名单'";
		return super.count(sql);
	}
	
	public int getFensCount(String fenID) {//查看有多少人关注同一个id
		String sql = "select count(*) from follow where FenID='"+fenID+"' and DeletedFlag is null and Relation !='黑名单'";
		return super.count(sql);
	}
	
	/*public Collection<Follow> getFollowByUserID(String userID,String rows, String pageNum){//查询好友
		String sql = "select * from follow where DeletedFlag is null and UserID = '"+userID+"' and Relation !='黑名单' order by Created_TS desc limit"
				+ ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(rows)) + " ," + Integer.parseInt(rows) + " ";
		return super.executeQuery(sql);
	}*/
	
	public Follow getFollow(String userID, String fenID){//查询是否存在,用于互相关注
		String sql = "select * from follow where DeletedFlag is null and UserID='"+userID+"' and FenID='"+fenID+"'";
		Collection<Follow> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Follow) result.toArray()[0];
		}else{
			return null;
		}
	}
	
//	public boolean updateRelation(String userID, String fenID, String relation){//设置好友关系
//		String sql = "update follow set Relation='"+relation+"' where UserID='"+userID+"' and FenID='"+fenID+"' and DeletedFlag is null";
//		return super.executeUpdate(sql);
//	}
	
	public Follow getByUID(String uid){//查询uid是否存在,用于取消关注
		String sql = "select * from follow where DeletedFlag is null and UID='"+uid+"'";
		Collection<Follow> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Follow) result.toArray()[0];
		}else{
			return null;
		}
	}
	
	
	public boolean cancel(String uid){//取消关注
		String sql = "update follow set DeletedFlag = 'Y' where UID = '"+uid+"'";
		return super.executeUpdate(sql);
	}
	//修改备注
	public boolean updateRemark(String remark,String uid){
		String sql = "update follow set Remark='"+remark+"' where DeletedFlag is null and UID='"+uid+"' and Relation!='黑名单'";
		return super.executeUpdate(sql);
	}
	/*
	public Collection<Follow> getNewFens(String UserID,String Relation){
		String sql = "select * from follow where DeletedFlag is null and UserID='"+UserID+"' and Relation='"+Relation+"'";
		return super.executeQuery(sql);
	}*/
	
	//好友提醒已查看
	public boolean updateStatus(String UID){
		String sql = "update follow set Status='1' where DeletedFlag is null and UID='"+UID+"' and Relation!='黑名单'";
		return super.executeUpdate(sql);
	}
	
	//拉入黑名单
	public boolean DrawInblackList(String UID){
		String sql = "update follow set Relation='黑名单' where DeletedFlag is null and UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	//获取黑名单
	/*public Collection<Follow> getBlackList(String UserID){
		String sql = "select * from follow where DeletedFlag is null and UserID='"+UserID+"' and Relation='黑名单'";
		return super.executeQuery(sql);
	}*/
	
	//移出黑名单
	public boolean removeBlackList(String UID){
		String sql = "update follow set DeletedFlag='Y' where UID='"+UID+"' and Relation='黑名单'";
		return super.executeUpdate(sql);
	}
	
}
