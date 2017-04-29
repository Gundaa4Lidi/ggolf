package com.galaxy.ggolf.dao;

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
		String sql = "update coachcourse set Verify='"+Verify+"' where DeletedFlag is null and CourseID='"+CourseID+"'";
		return super.executeUpdate(sql);
	}

}
