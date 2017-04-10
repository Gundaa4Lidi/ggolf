package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Coach;

public class CoachRowMapper implements RowMapper<Coach> {
    private static final String COLUMN_CoachID = "CoachID";
    private static final String COLUMN_UserID = "UserID";
    private static final String COLUMN_UserName = "UserName";
    private static final String COLUMN_UserHead = "UserHead";
    private static final String COLUMN_Age = "Age";
    private static final String COLUMN_College = "College";
    private static final String COLUMN_Seniority = "Seniority";
    private static final String COLUMN_Intro = "Intro";
    private static final String COLUMN_ACHV = "ACHV";
    private static final String COLUMN_TeachACHV = "TeachACHV";
    private static final String COLUMN_Verify = "Verify";
    private static final String COLUMN_Created_TS = "Created_TS";
    private static final String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public Coach mapRow(ResultSet res) throws SQLException {
	    String CoachID = res.getString(COLUMN_CoachID);
	    String UserID = res.getString(COLUMN_UserID);
	    String UserName = res.getString(COLUMN_UserName);
	    String UserHead = res.getString(COLUMN_UserHead);
	    String Age = res.getString(COLUMN_Age);
	    String College = res.getString(COLUMN_College);
	    String Seniority = res.getString(COLUMN_Seniority);
	    String Intro = res.getString(COLUMN_Intro);
	    String ACHV = res.getString(COLUMN_ACHV);
	    String TeachACHV = res.getString(COLUMN_TeachACHV);
	    String Verify = res.getString(COLUMN_Verify);
	    String Created_TS = res.getString(COLUMN_Created_TS);
	    String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new Coach(CoachID,UserID,UserName,UserHead,Age,College,Seniority,Intro,ACHV,TeachACHV,Verify,Created_TS,Updated_TS);
	}

}
