package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.OtherDateRowMapper;
import com.galaxy.ggolf.domain.OtherDate;

public class OtherDateDAO extends GenericDAO<OtherDate> {

	public OtherDateDAO() {
		super(new OtherDateRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(OtherDate od){
		String sql = "insert into otherdate(ClubID,ClubserveID,DateTime,Created_TS)values(?,?,?,?)";
		return super.executeUpdate(sql,od.getClubID(),od.getClubserveID(),od.getDateTime(),Time());
	}
	
	public boolean delete(String ClubserveID,String DateTime){
		String sql = "update otherdate set DeletedFlag='Y',Updated_TS='"+Time()+"'"
				+ " where DeletedFlag is null and ClubserveID='"+ClubserveID+"' and DateTime='"+DateTime+"'";
		return super.executeUpdate(sql);
	}
	
	public OtherDate check(String ClubserveID,String DateTime){
		String sql = "select * from otherdate where DeletedFlag is null and ClubserveID='"+ClubserveID+"' and DateTime='"+DateTime+"'";
		Collection<OtherDate> result = super.executeQuery(sql);
		if(result.size()>0){
			return (OtherDate) result.toArray()[0];
		}
		return null;
	}
	
	public Collection<OtherDate> getByClubserveID(String ClubserveID,String sqlString,String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from otherdate where DeletedFlag is null and ClubserveID='"+ClubserveID+"' "+sqlString+" order by DateTime desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	public int getCount(String ClubserveID,String sqlString){
		String sql = "select count(*) from otherdate where DeletedFlag is null and ClubserveID='"+ClubserveID+"' "+sqlString+"";
		return super.count(sql);
	}

}
