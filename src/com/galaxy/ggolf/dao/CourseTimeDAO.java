package com.galaxy.ggolf.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CourseTimeRowMapper;
import com.galaxy.ggolf.domain.CourseTime;

public class CourseTimeDAO extends GenericDAO<CourseTime> {

	public CourseTimeDAO() {
		super(new CourseTimeRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 创建
	 * @param ct
	 * @return
	 */
//	public boolean create(CourseTime ct){
//		String sql = "insert into coachscore(CourseID,"
//				+ "CoachID,"
//				+ "IsOpen,"
//				+ "OpenTime,"
//				+ "Created_TS)values(?,?,?,?,?)";
//		return super.executeUpdate(sql,ct.getCourseID(),ct.getCoachID(),ct.getIsOpen(),ct.getOpenTime(),Time());
//	}
	
	/**
	 * 根据课程编号获取开放时间的信息
	 * @param CourseID
	 * @return
	 */
	public Collection<CourseTime> getTimebyCourseID(String CourseID){
		String sql = "select * from coursetime where CourseID='"+CourseID+"' and DeletedFlag is null";
		return super.executeQuery(sql);
	}
	
	/**
	 * 根据日期,课程获取开放时间
	 * @param CourseID
	 * @param OpenTime
	 * @return
	 */
	public Collection<CourseTime> getTimeByDate(String CourseID, String OpenTime){
		String sql = "select * from coursetime where CourseID='"+CourseID+"' and date_format(`OpenTime`,'%Y-%m-%d')='"+OpenTime+"'"
				+ " and DeletedFlag is null";
		return super.executeQuery(sql);
	}
	
	/**
	 * 批量修改开放时间
	 * @param courseTimes
	 * @return
	 */
	public boolean IsOpenTime(Collection<CourseTime> courseTimes){
		Collection<String> sqls = new ArrayList<String>();
		for(CourseTime ct : courseTimes){
			if(getByOpenTime(ct.getOpenTime(),ct.getCourseID())!=null){
				String sql = "update coursetime set IsOpen='"+ct.getIsOpen()+"',Updated_TS='"+Time()
						+"' where CourseTimeID='"+ct.getCourseTimeID()+"' and DeletedFlag is null";
				sqls.add(sql);
			}else{
				String sql1 = "insert into coachscore(CourseID,"
						+ "CoachID,"
						+ "IsOpen,"
						+ "OpenTime,"
						+ "Created_TS)values('"+ct.getCoachID()
						+"','"+ct.getCoachID()
						+"','"+ct.getIsOpen()
						+"','"+ct.getOpenTime()
						+"','"+Time()+"')";
				sqls.add(sql1);
			}
			
		}
		return super.batchInsert(sqls);
	}
	
	/**
	 * 查看该课程的开课时间是否存在
	 * @param OpenTime
	 * @return
	 */
	public CourseTime getByOpenTime(String OpenTime,String CourseID){
		String sql = "select * from courseTime where DeletedFlag is null and CourseID='"+CourseID+"' and OpenTime='"+OpenTime+"'";
		Collection<CourseTime> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (CourseTime) result.toArray()[0];
		}
		return null;
	}
	
	public CourseTime getByCourseTimeID(String CourseTimeID){
		String sql = "select * from courseTime where DeletedFlag is null and CourseTimeID='"+CourseTimeID+"'";
		Collection<CourseTime> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (CourseTime) result.toArray()[0];
		}
		return null;
	}
	
	
	/**
	 * 关闭已过期的开放时间
	 * @param dateTime
	 * @return
	 */
	public void CloseTime(String dateTime){
		String sql = "update coursetime set DeletedFlag='Y',IsOpen='0',Updated_TS='"+Time()+"' where OpenTime < '"+dateTime+"'";
		if(super.executeUpdate(sql)){
			logger.debug("close time is {}",true);
		}
	}
}
