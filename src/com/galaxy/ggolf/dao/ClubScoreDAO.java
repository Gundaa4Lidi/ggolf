package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubScoreRowMapper;
import com.galaxy.ggolf.domain.ClubScore;

public class ClubScoreDAO extends GenericDAO<ClubScore> {

	public ClubScoreDAO() {
		super(new ClubScoreRowMapper());
	}
	
	public boolean create(ClubScore cs){
		String sql = "insert into clubscore("
				+ "UserID,"
				+ "UserName,"
				+ "UserHead,"
				+ "PhoneNum,"
				+ "ClubID,"
				+ "DesignScore,"
				+ "GrassScore,"
				+ "FacilityScore,"
				+ "ServiceScore,"
				+ "Content,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,cs.getUserID(),cs.getUserName(),cs.getUserHead(),cs.getPhoneNum(),
				cs.getClubID(),cs.getDesignScore(),cs.getGrassScore(),cs.getFacilityScore(),
				cs.getServiceScore(),cs.getContent(),Time1());
	}
	
	public ClubScore getClubScoreByUID(String UID){
		String sql = "select * from clubscore where UID='"+UID+"'";
		Collection<ClubScore> result = super.executeQuery(sql);
		if(result.size()>0){
			return (ClubScore) result.toArray()[0];
		}
		return null;
	}
	
	public Collection<ClubScore> getCommentByClubID(String ClubID,String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from clubscore where ClubID='"+ClubID+"' order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
	}
	
	public int getCommentCount(String ClubID){
		String sql = "select count(*) from clubscore where ClubID='"+ClubID+"'";
		return super.count(sql);
	}
	
	public ClubScore getClubScoreByUserID(String UserID,String ClubID){
		String sql = "select * from clubscore where UserID='"+UserID+"' and Created_TS='"+Time1()+"' and ClubID='"+ClubID+"'";
		Collection<ClubScore> result = super.executeQuery(sql);
		if(result.size()>0){
			return (ClubScore) result.toArray()[0];
		}
		return null;
	}
	
}
