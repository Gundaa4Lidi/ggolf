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
		String sql = "insert into coach(UserID,UserName,UserHead,Age,College,Seniority,Intro,ACHV,TeachACHV,Verify,Created_TS)values(?,?,?,?,?,?,?,?,?,?)";
		return super.sqlUpdate(sql,coach.getUserID(),coach.getUserName(),coach.getUserHead(),coach.getAge(),coach.getCollege(),
				coach.getSeniority(),coach.getIntro(),coach.getACHV(),coach.getTeachACHV(),"申请中",Time());
	}
	
	/**
	 * 获取全部教练信息
	 * @param rows
	 * @param sqlString
	 * @return
	 */
	public Collection<Coach> getAll(String rows,String sqlString){
		String limit = "";
		if(rows!=null||rows.equals("")){
			limit = "limit 0 , "+Integer.parseInt(rows)+"";
		}
		String sql = "select * from coach where DeletedFlag is null "+sqlString+" order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 修改教练信息
	 * @param c
	 * @param sqlString
	 * @return
	 */
	public boolean update(Coach c, String sqlString){
		String sql = "update coach set "+ sqlString +" Updated_TS='"+Time()+"' where CoachID='"+c.getCoachID()+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 查询教练信息是否存在
	 * @param UserID
	 * @return
	 */
	public Coach getCoachByUserID(String UserID){
		String sql = "select * from coach where DeletedFlag is null and UserID="+UserID+"";
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
		String sql = "update coach set Verify='"+verify+"' where CoachID='"+coachID+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
}
