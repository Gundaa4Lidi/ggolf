package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubserveLimitTimeRowMapper;
import com.galaxy.ggolf.domain.ClubserveLimitTime;

public class ClubserveLimitTimeDAO extends GenericDAO<ClubserveLimitTime> {

	public ClubserveLimitTimeDAO() {
		super(new ClubserveLimitTimeRowMapper());
	}
	
	
	/**
	 * 生成订单编号
	 * @return
	 */
	public int getClubserveLimitTimeID(){
		String[] s = new String[2];
		s[0] = "insert into clubservelimittime(`Num`)values(NULL)";
		s[1] = "select max(Num) as a  from clubservelimittime";
		return super.getId(s);
	}
	
	public boolean create(ClubserveLimitTime clt){
		if(clt.getClubserveLimitTimeID()!=null){
			String lt = "LT";
			String main = "000000";
			String count = clt.getClubserveLimitTimeID();
			String id = super.getCustomID(lt, main, count);
			clt.setClubserveLimitTimeID(id);
			String sql = "insert into clubservelimittime("
					+ "ClubserveLimitTimeID,"
					+ "ClubserveID,"
					+ "Price,"
					+ "StartTime,"
					+ "EndTime,"
					+ "IsUsed,"
					+ "Count,"
					+ "Date,"
					+ "ServiceExplain,"
					+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?) ";
			return super.executeUpdate(sql,clt.getClubserveLimitTimeID(),clt.getClubserveID(),clt.getPrice(),
					clt.getStartTime(),clt.getEndTime(),clt.getIsUsed(),clt.getCount(),clt.getDate(),clt.getServiceExplain(),Time());
		}
		return false;
	}
	
	public boolean update(ClubserveLimitTime clt){
		String sql = "update clubservelimittime set "
				+ "ClubserveID=?,"
				+ "Price=?,"
				+ "StartTime=?,"
				+ "EndTime=?,"
				+ "IsUsed=?,"
				+ "Count=?,"
				+ "Date=?,"
				+ "ServiceExplain=?,"
				+ "Updated_TS=? where ClubserveLimitTimeID=?";
		return super.executeUpdate(sql,clt.getClubserveID(),clt.getPrice(),clt.getStartTime(),
				clt.getEndTime(),clt.getIsUsed(),clt.getCount(),clt.getDate(),clt.getServiceExplain(),
				Time(),clt.getClubserveLimitTimeID());
	}
	
	public ClubserveLimitTime getLimitTimeByClubserveID(String ClubserveID,String Date){
		String sql = "select * from clubservelimittime where DeletedFlag is null and "
				+ "Date='"+Date+"' and ClubserveID='"+ClubserveID+"' ";
		Collection<ClubserveLimitTime> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (ClubserveLimitTime) result.toArray()[0];
		}
		return null;
	}
	
	public ClubserveLimitTime getByClubserveLimitTimeID(String ClubserveLimitTimeID){
		String sql = "select * from clubservelimittime where DeletedFlag is null and "
				+ "ClubserveLimitTimeID='"+ClubserveLimitTimeID+"'";
		Collection<ClubserveLimitTime> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (ClubserveLimitTime) result.toArray()[0];
		}
		return null;
	}
	
	public Collection<ClubserveLimitTime> getbyClubserveID(String ClubserveID){
		String sql = "select * from clubservelimittime where DeletedFlag is null and ClubserveID='"+ClubserveID+"'";
		return super.executeQuery(sql);
	}
	
	
	public boolean delete(String ClubserveLimitTimeID){
		String sql = "update clubservelimittime set DeletedFlag='Y' where ClubserveLimitTimeID='"+ClubserveLimitTimeID+"'";
		return super.executeUpdate(sql);
	}

}
