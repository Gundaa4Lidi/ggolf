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
				+ "UserName,"
				+ "CoachName,"
				+ "CoachHead,"
				+ "CoachPhone,"
				+ "Birthday,"
				+ "Sex,"
				+ "ClubID,"
				+ "ClubName,"
				+ "Seniority,"
				+ "Intro,"
				+ "ACHV,"
				+ "TeachACHV,"
				+ "Verify,"
				+ "IsRead,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.sqlUpdate(sql,coach.getCoachID(),coach.getUserName(),coach.getCoachName(),
				coach.getCoachHead(),coach.getCoachPhone(),coach.getBirthday(),coach.getSex(),
				coach.getClubID(),coach.getClubName(),coach.getSeniority(),coach.getIntro(),
				coach.getACHV(),coach.getTeachACHV(),"0","0",Time());
	}
	
	/**
	 * 根据关键字,页码,条数,学院编号获取教练
	 * @param rows
	 * @param sqlString
	 * @return
	 */
	public Collection<Coach> getAll(String rows,String sqlString){
		String limit = super.limit(null, rows);
		String sql = "select * from coach where DeletedFlag is null "+sqlString+" order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 获取教练的数量
	 * @param sqlString
	 * @return
	 */
	public int getAllCount(String sqlString){
		String sql = "select count(*) from coach where DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	/**
	 * 根据学院编号获取教练
	 * @param ClubID
	 * @return
	 */
	public Collection<Coach> getCoachByClubID(String ClubID){
		String sql = "select * from coach where DeletedFlag is null and ClubID='"+ClubID+"' and Verify='1' order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	/**
	 * 根据学院编号获取教练数量
	 * @param ClubID
	 * @return
	 */
	public int getCountByClubID(String ClubID){
		String sql = "select count(*) from coach where DeletedFlag is null and ClubID='"+ClubID+"' and Verify='1'";
		return super.count(sql);
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
	 * 查询验证通过的有没这个教练
	 * @param UserID
	 * @return
	 */
	public Coach getCoachByCoachID(String CoachID,String Verify){
		String sqlString = "";
		if(Verify!=null){
			sqlString += "and Verify='"+Verify+"' ";
		}
		String sql = "select * from coach where DeletedFlag is null and CoachID='"+CoachID+"' "+sqlString+"";
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
	
	/**
	 * 删除审核过的申请信息
	 * @param coachID
	 * @return
	 */
	public boolean delete(String coachID){
		String sql = "update coach set DeletedFlag='Y', Updated_TS='"+Time()+"' where CoachID='"+coachID+"' and DeletedFlag is null and Verify!='0'";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 获取未查阅的数量
	 * @return
	 */
	public int getRealCount(){
		String sql= "select count(*) from coach where DeletedFlag is null and Verify='0' and IsRead='0' ";
		return super.count(sql);
	}
	
	/**
	 * 设置已查阅
	 * @param coachID
	 * @return
	 */
	public boolean IsRead(String coachID){
		String sql = "update coach set IsRead='1', Updated_TS='"+Time()+"'where IsRead='0' and CoachID='"+coachID+"'";
		return super.executeUpdate(sql);
	}
}
