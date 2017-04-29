package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubTotalScoreRowMapper;
import com.galaxy.ggolf.domain.ClubTotalScore;

public class ClubTotalScoreDAO extends GenericDAO<ClubTotalScore> {

	public ClubTotalScoreDAO() {
		super(new ClubTotalScoreRowMapper());
	}
	
	/**
	 * 获取球场各项评分的平均分
	 * @param ClubID
	 * @return
	 */
	public ClubTotalScore getTotalScoreByClubID(String ClubID){
		String sql = "select ClubID,"
				+ "cast(AVG(DesignScore) as decimal(10,1)) as DesignScore,"
				+ "cast(AVG(GrassScore) as decimal(10,1)) as GrassScore,"
				+ "cast(AVG(FacilityScore) as decimal(10,1)) as FacilityScore,"
				+ "cast(AVG(ServiceScore) as decimal(10,1)) as ServiceScore "
				+ "from clubscore where ClubID='"+ClubID+"' Group by ClubID";
		Collection<ClubTotalScore> result = super.executeQuery(sql);
		if(result.size()>0){
			return (ClubTotalScore) result.toArray()[0];
		}
		return null;
	}
}
