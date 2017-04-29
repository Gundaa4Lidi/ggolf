package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Score;

public class ScoreRowMapper implements RowMapper<Score> {
    private static final String COLUMN_UserId="UserId";
    private static final String COLUMN_ScoreId="ScoreId";
    private static final String COLUMN_PlayerName="PlayerName";
    private static final String COLUMN_ClubName="ClubName";
    private static final String COLUMN_SiteOnePAR="SiteOnePAR";
    private static final String COLUMN_SiteTwoPAR="SiteTwoPAR";
    private static final String COLUMN_ScoresOne="ScoresOne";
    private static final String COLUMN_ScoresTwo="ScoresTwo";
    private static final String COLUMN_PuttersOne="PuttersOne";
    private static final String COLUMN_PuttersTwo="PuttersTwo";
    private static final String COLUMN_Created_TS="Created_TS";
    private static final String COLUMN_Status="Status";
	@Override
	public Score mapRow(ResultSet res) throws SQLException {
		String UserId=res.getString(COLUMN_UserId);
		String ScoreId=res.getString(COLUMN_ScoreId);
		String PlayerName=res.getString(COLUMN_PlayerName);
        String ClubName=res.getString(COLUMN_ClubName);
		String SiteOnePAR=res.getString(COLUMN_SiteOnePAR);
	    String SiteTwoPAR=res.getString(COLUMN_SiteTwoPAR);
	    String ScoresOne=res.getString(COLUMN_ScoresOne);
	    String ScoresTwo=res.getString(COLUMN_ScoresTwo);
	    String PuttersOne=res.getString(COLUMN_PuttersOne);
	    String PuttersTwo=res.getString(COLUMN_PuttersTwo);
		String Created_TS=res.getString(COLUMN_Created_TS);
		String Status=res.getString(COLUMN_Status);
		return new Score(UserId,ScoreId,PlayerName,ClubName,SiteOnePAR,SiteTwoPAR,ScoresOne,ScoresTwo,PuttersOne,PuttersTwo,Created_TS,Status);
	}

}
