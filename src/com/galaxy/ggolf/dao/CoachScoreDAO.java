package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CoachScoreRowMapper;
import com.galaxy.ggolf.domain.CoachScore;

public class CoachScoreDAO extends GenericDAO<CoachScore> {

	public CoachScoreDAO() {
		super(new CoachScoreRowMapper());
	}
	
	public boolean create(CoachScore cs){
		String sql = "insert into coachscore(CoachID,"
				+ "Score,"
				+ "TeachNo,"
				+ "Created_TS)values(?,?,?,?)";
		return super.executeUpdate(sql,cs.getCoachID(),cs.getScore(),
				cs.getTeachNo(),Time());
	}
	
	public boolean update(String sqlString,String CoachID){
		String sql = "update coachscore set "+sqlString+" Updated_TS='"+Time()+"' where CoachID='"+CoachID+"'";
		return super.executeUpdate(sql);
	}
	
	public CoachScore getByCoachID(String CoachID){
		String sql = "select * from coachscore where CoachID='"+CoachID+"'";
		Collection<CoachScore> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (CoachScore) result.toArray()[0];
		}
		return null;
	}
	
	
}
