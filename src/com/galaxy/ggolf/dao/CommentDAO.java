package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CommentRowMapper;
import com.galaxy.ggolf.domain.Comment;
import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.jdbc.CommonConfig;

public class CommentDAO extends GenericDAO<Comment> {

	public CommentDAO() {
		super(new CommentRowMapper());
		// TODO Auto-generated constructor stub
	}

	//创建一条评论或回复
	public boolean create(Comment com){
		String sql = "insert into comment(Action,"
				+ "RootType,"
				+ "RootID,"
				+ "UserName,"
				+ "UserHead,"
				+ "UserID,"
				+ "ParentType,"
				+ "ParentID,"
				+ "ParentUserID,"
				+ "Memo,"
				+ "ReplyID,"
				+ "ReplyName,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.sqlUpdate(sql, com.getAction(),com.getRootType(),com.getRootID(),com.getUserName(),com.getUserHead(),com.getUserID(),
				com.getParentType(),com.getParentID(),com.getParentUserID(),com.getMemo(),com.getReplyID(),com.getReplyName(),Time());
	}
	
	//查询该评论是否存在
	public Comment getByCommentID(String commentID) {
		String sql = "select * from comment where CommentID='" + commentID + "'AND DeletedFlag is null";
		Collection<Comment> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return (Comment) result.toArray()[0];
		} else {
			return null;
		}
	}
	
	//获取所有评论
	public Collection<Comment> getAll(String sqlString,String rows){
		String sql = "select * from comment where DeletedFlag is null and Action='"
				+ CommonConfig.ACTION_COMMENT +"' "+sqlString+" order by Created_TS desc "
				+ "limit 0 , "+Integer.parseInt(rows)+"";
		return super.executeQuery(sql);
	}
	
	//获取全部评论的数量
	public int getCommentCount(String sqlString){
		String sql = "select count(*) from comment where DeletedFlag is null and Action='"
				+ CommonConfig.ACTION_COMMENT +"' "+ sqlString +"";
		return super.count(sql);
	}
	
	/**
	 * 获取全部评论和回复
	 * @param sqlString
	 * @param rows
	 * @return
	 */
	public Collection<Comment> getCommentBySearch(String sqlString,String rows){
		String sql = "select count(*) from comment where DeletedFlag is null "+ sqlString +" order by Created_TS desc "
				+ "limit 0 , "+Integer.parseInt(rows)+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 获取全部评论和回复的数量
	 * @param sqlString
	 * @return
	 */
	public int getCount(String sqlString){
		String sql = "select count(*) from comment where DeletedFlag is null "+ sqlString +"";
		return super.count(sql);
	}
	
	//根据类型获取评论和回复
	public Collection<Comment> getCommentByApp(String rows, String sqlString, String RootID, String RootType){
		String sql = "select * from comment where "
				+ "DeletedFlag is null and RootID='"+RootID+"'"
				+ "and RootType='"+RootType+"' "+ sqlString +" order by Created_TS "
				+ "limit 0 ," + Integer.parseInt(rows) + " ";
		return super.executeQuery(sql);
	}
	
	//获取该类型的评价和回复数量
	public int getCountByApp(String sqlString, String RootID,String RootType){
		String sql = "select count(*) from comment where "
				+ "DeletedFlag is null and RootID='"+RootID+"'"
				+ "and RootType='"+RootType+"' "+sqlString+"";
		return super.count(sql);
	}
	
	//根据类型获取评论(类型:球场,动态,足迹,文章)
	public Collection<Comment> getCommentByRoot(String rows,String sqlString,String RootID,String RootType){
		String sql = "select * from comment where Action='"+CommonConfig.ACTION_COMMENT+"' "
				+ "and DeletedFlag is null and RootID='"+RootID+"'"
				+ "and RootType='"+RootType+"' "+sqlString+" order by Created_TS desc limit "
				+ "0 ," + Integer.parseInt(rows) + "";
		return super.executeQuery(sql);
	}
	
	//根据类型获取评论数量(类型:球场,动态,足迹,文章)
	public int getCommentCountByRoot(String sqlString,String RootID,String RootType){
		String sql = "select count(*) from comment where Action='"+CommonConfig.ACTION_COMMENT+"' "
				+ "and DeletedFlag is null and RootID='"+RootID+"'"
				+ "and RootType='"+RootType+"' "+sqlString+"";
		return super.count(sql);
	}
	
	//根据类型获取评论的数量
	public int getCountByRoot(String sqlString, String RootID,String RootType){
		String sql = "select count(*) from comment where DeletedFlag is null and RootID='"+RootID+"'"
				+ "and RootType='"+RootType+"' "+sqlString+"";
		return super.count(sql);
	}
	
	//获取该条评论的回复
	public Collection<Comment> getCommentByReply(String rows,String UserID,String CommentID){
		String sql = "select * from comment where Action='"+CommonConfig.ACTION_REPLY+"' and DeletedFlag is null and "
				+ "ParentID='"+CommentID+"' and ParentUserID ='"+UserID+"' and ParentType='"+CommonConfig.TYPE_USER_COMMENT
				+"' order by Created_TS limit 0 ,"+Integer.parseInt(rows)+" ";
		return super.executeQuery(sql);
	}
	
	//获取该条评论的回复数量
 	public int getCountByReply(String UserID,String CommentID){
 		String sql = "select count(*) from comment where Action='"+CommonConfig.ACTION_REPLY+"' and DeletedFlag is null and "
				+ "ParentID='"+CommentID+"' and ParentUserID ='"+UserID+"' and ParentType='"+CommonConfig.TYPE_USER_COMMENT+"'";
 		return super.count(sql);
 	}
	
	//删除评论
	public boolean delete(String CommentID){
		String sql = "update comment set DeletedFlag='Y' where CommentID='"+CommentID+"'";
		return super.executeUpdate(sql);
	}
	
	//获取时间分组
	public Collection<Comment> getDTGroup(String sqlString,String rows){
		String sql = "select * from comment where DeletedFlag is null"
				+ " "+sqlString+" GROUP BY date_format(`Created_TS`,'%Y-%m-%d')"
				+ " order by Created_TS desc limit 0 , "+Integer.parseInt(rows)+"";
		return super.executeQuery(sql);
	}
}
