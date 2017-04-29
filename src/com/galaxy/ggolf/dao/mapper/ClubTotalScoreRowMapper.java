package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.ClubTotalScore;

public class ClubTotalScoreRowMapper implements RowMapper<ClubTotalScore> {
	private static final String COLUMN_ClubID = "ClubID";
	private static final String COLUMN_DesignScore = "DesignScore";
	private static final String COLUMN_GrassScore = "GrassScore";
	private static final String COLUMN_FacilityScore = "FacilityScore";
	private static final String COLUMN_ServiceScore = "ServiceScore";

	@Override
	public ClubTotalScore mapRow(ResultSet res) throws SQLException {
		String ClubID = res.getString(COLUMN_ClubID);
		String DesignScore = res.getString(COLUMN_DesignScore);
		String GrassScore = res.getString(COLUMN_GrassScore);
		String FacilityScore = res.getString(COLUMN_FacilityScore);
		String ServiceScore = res.getString(COLUMN_ServiceScore);
		return new ClubTotalScore(ClubID,DesignScore,GrassScore,FacilityScore,ServiceScore);
	}

}
