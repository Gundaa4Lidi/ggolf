package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubFairwayRowMapper;
import com.galaxy.ggolf.domain.ClubDetail;
import com.galaxy.ggolf.domain.ClubFairway;
import com.galaxy.ggolf.tools.ListUtil;

public class ClubFairwayDAO extends GenericDAO<ClubFairway> {

	public ClubFairwayDAO() {
		super(new ClubFairwayRowMapper());
		// TODO Auto-generated constructor stub
	}

	public Collection<ClubFairway> getClubFairway(String clubID){
		String sql = "select * from clubfairway where DeletedFlag is null and ClubID = '"+ clubID +"'";
		return super.executeQuery(sql);
	}
	
	public ClubFairway getClubFairwayByUID(String UID){
		String sql = "select * from clubfairway where UID='"+UID+"' and DeletedFlag is null ";
		Collection<ClubFairway> club = super.executeQuery(sql);
		if(club.size() > 0){
			return (ClubFairway) club.toArray()[0];
		}else{
			return null;
		}
	}
	
	
	public boolean create(ClubFairway clubFairway){
		String photo = "";
		String par = "";
		if(clubFairway.getPar().size()>0){
			par = new ListUtil().ListToString(clubFairway.getPar(),",");
		}
		if(clubFairway.getPhoto()!=null){
			photo = new ListUtil().ListToString(clubFairway.getPhoto());
		}
		String sql = "insert into clubfairway(ClubID,HoleNum,FairwayName,Updated_TS,Photo,Par)values('"
					+ clubFairway.getClubID() 
					+"','"
					+ clubFairway.getHoleNum() 
					+"','"
					+ clubFairway.getFairwayName() 
					+"','"
					+ Time()
					+"','"
					+ photo
					+"','" 
					+ par+"')";
		return super.executeUpdate(sql);
	}
	
	/**
	 * 查看同一球场是否存在相同名称的球道
	 * @param FairwayName
	 * @param ClubID
	 * @return
	 */
	public ClubFairway getFairwayNameByClubID(String FairwayName,String ClubID){
		String sql = "select * from clubfairway where FairwayName='"+FairwayName+"' and ClubID='"+ClubID+"'";
		Collection<ClubFairway> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (ClubFairway) result.toArray()[0];
		}
		return null;
	}

	public boolean update(ClubFairway clubFairway){
		String photo = "";
		String par = "";
		if(clubFairway.getPar().size()>0){
			par = new ListUtil().ListToString(clubFairway.getPar(),",");
		}
		if(clubFairway.getPhoto()!=null){
			photo = new ListUtil().ListToString(clubFairway.getPhoto());
		}
		String sql = "update clubfairway set HoleNum=?,FairwayName=?,Updated_TS=?,Par=?,Photo=? where UID=? and DeletedFlag is null";
		return super.sqlUpdate(sql, clubFairway.getHoleNum(),clubFairway.getFairwayName(),Time(),par,photo,clubFairway.getUID());
	}
	
	public boolean delete(String uid){
		String sql = "update clubfairway set DeletedFlag = 'Y',Updated_TS = '"+ Time() +"' WHERE UID = '"+uid+"'";
		return super.executeUpdate(sql);
	}
	
	


}
