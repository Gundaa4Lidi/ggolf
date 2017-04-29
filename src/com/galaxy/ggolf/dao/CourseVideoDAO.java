package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CourseVideoRowMapper;
import com.galaxy.ggolf.domain.CourseVideo;

public class CourseVideoDAO extends GenericDAO<CourseVideo> {

	public CourseVideoDAO() {
		super(new CourseVideoRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean create(CourseVideo cv){
		String sql = "insert into coursevideo(CreatorID,"
				+ "RoomID,"
				+ "Password,"
				+ "RoomName)values(?,?,?,?)";
		return super.executeUpdate(sql,cv.getCreatorID(),cv.getRoomID(),
				cv.getPassword(),cv.getRoomName());
	}
	
	public boolean updatePassword(String pwd,String UID){
		String sql = "update coursevideo set Password='"+pwd+"' WHERE DeletedFlag is null and UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	public boolean delete(String UID){
		String sql = "update coursevideo set DeletedFlag='Y' WHER UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	public Collection<CourseVideo> getVideoByCoachID(String CoachID){
		String sql = "select * from coursevideo where CreatorID=("
				+ "select CoachID from coachcourse where DeletedFlag is null"
				+ " and CoachID='"+CoachID+"' and IsVideo='1' ) and Verify='申请通过'";
		return super.executeQuery(sql);
	}

}
