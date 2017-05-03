package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CourseVideoRowMapper;
import com.galaxy.ggolf.domain.CourseVideo;

public class CourseVideoDAO extends GenericDAO<CourseVideo> {

	public CourseVideoDAO() {
		super(new CourseVideoRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 创建直播间
	 * @param cv
	 * @return
	 */
	public boolean create(CourseVideo cv){
		String sql = "insert into coursevideo(CreatorID,"
				+ "CourseID,"
				+ "RoomID,"
				+ "Password,"
				+ "RoomName,"
				+ "Created_TS)values(?,?,?,?,?,?)";
		return super.executeUpdate(sql,cv.getCreatorID(),cv.getRoomID(),cv.getCourseID(),
				cv.getPassword(),cv.getRoomName(),Time());
	}
	
	/**
	 * 修改直播间的密码
	 * @param pwd
	 * @param UID
	 * @return
	 */
	public boolean updatePassword(String pwd,String CourseID){
		String sql = "update coursevideo set Password='"+pwd+"' Updated_TS='"+Time()+"' WHERE DeletedFlag is null and CourseID='"+CourseID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 修改直播间的房间名称
	 * @param roomName
	 * @param UID
	 * @return
	 */
	public boolean updateRoomName(String roomName,String UID){
		String sql = "update coursevideo set RoomName='"+roomName+"',Updated_TS='"+Time()+"' WHERE DeletedFlag is null and UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	
	/**
	 * 删除房间
	 * @param UID
	 * @return
	 */
	public boolean delete(String UID){
		String sql = "update coursevideo set DeletedFlag='Y' Updated_TS='"+Time()+"' WHER UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 根据教练编号获取教练所有的直播间信息
	 * @param CoachID
	 * @return
	 */
	public Collection<CourseVideo> getVideoByCoachID(String CoachID){
		String sql = "select * from coursevideo where DeletedFlag is null and  CreatorID='"+CoachID+"' and ("
				+ "select count(*) from coachcourse where DeletedFlag is null"
				+ " and CoachID='"+CoachID+"' and IsVideo='1' and IsOpen='1' and Verify='1')>0 ";
		return super.executeQuery(sql);
	}
	
	/**
	 * 根据课程编号获取直播间的信息
	 * @param CourseID
	 * @return
	 */
	public CourseVideo getByCourseID(String CourseID){
		String sql = "select * from coursevideo where DeletedFlag is null and CourseID=("
				+ "select CourseID from coachcourse where DeletedFlag is null"
				+ " and CourseID='"+CourseID+"' and IsVideo='1' and IsOpen='1' and Verify='1') ";
		Collection<CourseVideo> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (CourseVideo) result.toArray()[0];
		}
		return null;
	}
	

}
