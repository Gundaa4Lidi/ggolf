package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.ClubScore;

public class ClubScoreRowMapper implements RowMapper<ClubScore> {
	private static final String COLUMN_UID = "UID";
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_UserName = "UserName";
	private static final String COLUMN_UserHead = "UserHead";
	private static final String COLUMN_PhoneNum = "PhoneNum";
	private static final String COLUMN_ClubID = "ClubID";
	private static final String COLUMN_DesignScore = "DesignScore";
	private static final String COLUMN_GrassScore = "GrassScore";
	private static final String COLUMN_FacilityScore = "FacilityScore";
	private static final String COLUMN_ServiceScore = "ServiceScore";
	private static final String COLUMN_Content = "Content";
	private static final String COLUMN_Created_TS = "Created_TS";
	@Override
	public ClubScore mapRow(ResultSet res) throws SQLException {
	    String UID = res.getString(COLUMN_UID);
	    String UserID = res.getString(COLUMN_UserID);
	    String UserName = res.getString(COLUMN_UserName);
	    String UserHead = res.getString(COLUMN_UserHead);
	    String PhoneNum = res.getString(COLUMN_PhoneNum);
	    String ClubID = res.getString(COLUMN_ClubID);
	    String DesignScore = res.getString(COLUMN_DesignScore);
	    String GrassScore = res.getString(COLUMN_GrassScore);
	    String FacilityScore = res.getString(COLUMN_FacilityScore);
	    String ServiceScore = res.getString(COLUMN_ServiceScore);
	    String Content = res.getString(COLUMN_Content);
	    String Created_TS = res.getString(COLUMN_Created_TS);
		return new ClubScore(UID,UserID,UserName,UserHead,PhoneNum,ClubID,DesignScore,GrassScore,FacilityScore,ServiceScore,Content,Created_TS);
	}

}
