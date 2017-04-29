package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CoachCourseRowMapper;
import com.galaxy.ggolf.domain.CoachCourse;

public class CoachCourseDAO extends GenericDAO<CoachCourse> {

	public CoachCourseDAO() {
		super(new CoachCourseRowMapper());
	}
	
	public boolean create(CoachCourse cc){
		String sql = "insert into coachcourse(CoachID,"
				+ "CoachName,"
				+ "CoachHead,"
				+ "CoachPhone,"
				+ "Title,"
				+ "Price,"
				+ "Verify,"
				+ "Valid,"
				+ "IsOpen,"
				+ "MaxPeople,"
				+ "ContainExplain,"
				+ "IsVideo,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,cc.getCoachID(),cc.getCoachName(),cc.getCoachPhone(),cc.getTitle(),cc.getPrice(),
				"申请中",cc.getValid(),"0",cc.getMaxPeople(),cc.getContainExplain(),cc.getIsVideo(),Time());
	}
	
	public boolean CourseVerify(String CourseID,String Verify){
		String sql = "update coachcourse set Verify='"+Verify+"',Updated_TS='"+Time()+"' where DeletedFlag is null and CourseID='"+CourseID+"'";
		return super.executeUpdate(sql);
	}
	
	
	public Collection<CoachCourse> getCourse(String CoachID,String sqlString,String pageNum,String rows){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from coachcourse where DeletedFlag is null"
				+ " and CoachID='"+CoachID+"' "+sqlString+" order by Created_TS "+limit+"";
		return super.executeQuery(sql);
	}
	
	public CoachCourse getByCourseID(String CourseID){
		String sql = "select * from coachcourse where CourseID='"+CourseID+"' and DeletedFlag is null";
		Collection<CoachCourse> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (CoachCourse) result.toArray()[0];
		}
		return null;
	}
	
	
	public boolean update(String sqlString,String CourseID){
		String sql = "update coachcourse set "+sqlString+" Updated_TS='"+Time()+"' "
				+ "where DeletedFlag is null and CourseID='"+CourseID+"'";
		return super.executeUpdate(sql);
	}

}
