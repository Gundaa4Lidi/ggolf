package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CourseTimeRowMapper;
import com.galaxy.ggolf.domain.CourseTime;

public class CourseTimeDAO extends GenericDAO<CourseTime> {

	public CourseTimeDAO() {
		super(new CourseTimeRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(CourseTime ct){
		String sql = "insert into coachscore(CourseID,"
				+ "CoachID,"
				+ "IsOpen,"
				+ "OpenDate,"
				+ "OpenTime,"
				+ "Created_TS)values(?,?,?,?,?,?)";
		return super.executeUpdate(sql,ct.getCourseID(),ct.getCoachID(),ct.getIsOpen(),ct.getOpenDate(),ct.getOpenTime(),Time());
	}
	
	public Collection<CourseTime> getTimebyCourseID(String CourseID){
		String sql = "select * from coursetime where CourseID='"+CourseID+"' and DeletedFlag is null";
		return super.executeQuery(sql);
	}
}
