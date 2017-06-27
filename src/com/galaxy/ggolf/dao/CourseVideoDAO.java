package com.galaxy.ggolf.dao;

import java.util.ArrayList;
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
				+ "Created_TS,"
				+ "LiveEvent)values(?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,cv.getCreatorID(),cv.getCourseID(),cv.getRoomID(),
				cv.getPassword(),cv.getRoomName(),Time(),Time()+",房主创建直播间,"+cv.getCreatorID()+"|");
	}
	
	/**
	 * 修改直播间的密码
	 * @param pwd
	 * @param UID
	 * @return
	 */
	public boolean updatePassword(String pwd,String CourseID){
		String sql = "update coursevideo set Password='"+pwd+"',`LiveEvent` = concat('"+Time()+",修改房间密码,00|',`LiveEvent`),"
				+ " Updated_TS='"+Time()+"' WHERE DeletedFlag is null and CourseID='"+CourseID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 修改直播间的房间名称
	 * @param roomName
	 * @param UID
	 * @return
	 */
	public boolean updateRoomName(String roomName,String UID){
		String sql = "update coursevideo set RoomName='"+roomName+"',`LiveEvent` = concat('"+Time()+",修改房间主题为:"+roomName+",00|',`LiveEvent`),"
				+ "Updated_TS='"+Time()+"' WHERE DeletedFlag is null and UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 用户进入房间的记录
	 * @param UserName
	 * @param UserID
	 * @param UID
	 * @return
	 */
	public boolean intoRoom(String UserName, String UserID, String UID){
		String sql = "update coursevideo set `LiveEvent` = concat('"+Time()+","+UserName+"进入房间,"+UserID+"|',`LiveEvent`),"
				+ "Updated_TS='"+Time()+"' where DeletedFlag is null and UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	
	/**
	 * 删除房间
	 * @param RoomID
	 * @return
	 */
	public boolean delete(String RoomID){
		String sql = "update coursevideo set DeletedFlag='Y', Updated_TS='"+Time()+"' where RoomID='"+RoomID+"'";
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
				+ " and CoachID='"+CoachID+"' and IsVideo='1' and Verify='1')>0 "
				+ "order by Created_TS desc";
		return GetCVList(super.executeQuery(sql));
	}
	
	/**
	 * 根据课程编号获取直播间的信息
	 * @param CourseID
	 * @return
	 */
	public CourseVideo getByCourseID(String CourseID){
		String sql = "select * from coursevideo where DeletedFlag is null and CourseID='"+CourseID+"'";
		Collection<CourseVideo> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (CourseVideo) result.toArray()[0];
		}
		return null;
	}
	
	/**
	 * 查看直播间信息
	 * @param UID
	 * @return
	 */
	public CourseVideo getByUID(String UID){
		String sql = "select * from coursevideo where DeletedFlag is null and UID='"+UID+"'";
		Collection<CourseVideo> result = super.executeQuery(sql);
		if(result.size() > 0){
			return GETCV((CourseVideo) result.toArray()[0]);
		}
		return null;
	}
	
	/**
	 * 根据条件获取直播间
	 * @param sqlString
	 * @param rows
	 * @return
	 */
	public Collection<CourseVideo> getBySearch(String sqlString,String rows,String pageNum){
		String limit= super.limit(pageNum, rows);
		String sql = "select * from coursevideo where DeletedFlag is null "+sqlString+" order by Created_TS desc "+limit+"";
		return GetCVList(super.executeQuery(sql));
	}
	
	/**
	 * 获取所有直播间数量
	 * @param sqlString
	 * @return
	 */
	public int getBySearchCount(String sqlString){
		String sql = "select count(*) from coursevideo where DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	
	private CourseVideo GETCV(CourseVideo cv){
		cv = new CourseVideo(cv.getUID(), cv.getCreatorID(), cv.getCourseID(),
				cv.getRoomID(), cv.getRoomName(), cv.getCreated_TS(),
				cv.getUpdated_TS(), cv.getLiveEvent());
		return cv;
	}
	
	private Collection<CourseVideo> GetCVList(Collection<CourseVideo> list){
		Collection<CourseVideo> cvList = new ArrayList<CourseVideo>();
		CourseVideo cv = new CourseVideo();
		for(CourseVideo c : list){
			cv = GETCV(c);
			cvList.add(cv);
		}
		return cvList;
		
	}

}
