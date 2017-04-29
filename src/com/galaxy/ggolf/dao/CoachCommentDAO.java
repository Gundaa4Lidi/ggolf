package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CoachCommentRowMapper;
import com.galaxy.ggolf.domain.CoachComment;

public class CoachCommentDAO extends GenericDAO<CoachComment> {

	public CoachCommentDAO() {
		super(new CoachCommentRowMapper());
	}
	
	public boolean create(CoachComment cc){
		String sql = "insert into coachcomment(CoachID,"
				+ "UserID,"
				+ "UserHead,"
				+ "UserName,"
				+ "Score,"
				+ "Content,"
				+ "Created_TS)values(?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,cc.getCoachID(),cc.getUserID(),cc.getUserHead(),cc.getUserName(),
				cc.getScore(),cc.getContent(),Time());
	}
	
	public Collection<CoachComment> getCommentByCoachID(String CoachID){
		String sql = "select * from coachcomment where CoachID='"+CoachID+"' order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	public double getAvgScore(String CoachID){
		String sql = "select cast(AVG(Score) as decimal(10,1)) from coachcomment where CoachID='"+CoachID+"'";
		return super.avg(sql);
	}
}
