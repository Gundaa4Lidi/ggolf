package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CoachRowMapper;
import com.galaxy.ggolf.domain.Coach;

public class CoachDAO extends GenericDAO<Coach> {

	public CoachDAO() {
		super(new CoachRowMapper());
	}

	/**
	 * 创建教练信息
	 * @param coach
	 * @return
	 */
	public boolean create(Coach coach){
		String sql = "insert into coach(CoachID,"
				+ "CoachName,"
				+ "CoachHead,"
				+ "Age,"
				+ "ClubID,"
				+ "ClubName,"
				+ "Seniority,"
				+ "Intro,"
				+ "ACHV,"
				+ "TeachACHV,"
				+ "Verify,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?)";
		return super.sqlUpdate(sql,coach.getCoachID(),coach.getCoachName(),coach.getCoachHead(),coach.getAge(),coach.getClubID(),
				coach.getClubName(),coach.getSeniority(),coach.getIntro(),coach.getACHV(),coach.getTeachACHV(),"申请中",Time());
	}
	
	/**
	 * 根据关键字,页码,条数,学院编号获取教练
	 * @param rows
	 * @param sqlString
	 * @return
	 */
	public Collection<Coach> getAll(String limit,String sqlString){
		String sql = "select * from coach where DeletedFlag is null "+sqlString+" order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 根据学院编号获取教练
	 * @param ClubID
	 * @return
	 */
	public Collection<Coach> getCoachByClubID(String ClubID){
		String sql = "select * from coach where DeletedFlag is null and ClubID='"+ClubID+"' order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	/**
	 * 修改教练信息
	 * @param c
	 * @param sqlString
	 * @return
	 */
	public boolean update(String CoachID, String sqlString){
		String sql = "update coach set "+ sqlString +" Updated_TS='"+Time()+"' where CoachID='"+CoachID+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 查询教练信息是否存在
	 * @param UserID
	 * @return
	 */
	public Coach getCoachByCoachID(String CoachID){
		String sql = "select * from coach where DeletedFlag is null and CoachID="+CoachID+"";
		Collection<Coach> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Coach) result.toArray()[0];
		}
		return null;
	}
	
	/**
	 * 修改教练验证信息
	 * @param coachID
	 * @param verify
	 * @return
	 */
	public boolean updateVerify(String coachID,String verify){
		String sql = "update coach set Verify='"+verify+"', Updated_TS='"+Time()+"' where CoachID='"+coachID+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
}
