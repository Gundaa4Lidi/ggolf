package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Coach;

public class CoachRowMapper implements RowMapper<Coach> {
    private static final String COLUMN_UID = "UID";
	private static final String COLUMN_CoachID = "CoachID";
	private static final String COLUMN_CoachName = "CoachName";
	private static final String COLUMN_CoachHead = "CoachHead";
	private static final String COLUMN_Age = "Age";
	private static final String COLUMN_ClubID = "ClubID";
	private static final String COLUMN_ClubName = "ClubName";
	private static final String COLUMN_Seniority = "Seniority";
	private static final String COLUMN_Intro = "Intro";
	private static final String COLUMN_ACHV = "ACHV";
	private static final String COLUMN_TeachACHV = "TeachACHV";
	private static final String COLUMN_Verify = "Verify";
	private static final String COLUMN_TeachLocation = "TeachLocation";
	private static final String COLUMN_TeachCollege = "TeachCollege";
	private static final String COLUMN_TeachAddress = "TeachAddress";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public Coach mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String CoachID = res.getString(COLUMN_CoachID);
		String CoachName = res.getString(COLUMN_CoachName);
		String CoachHead = res.getString(COLUMN_CoachHead);
		String Age = res.getString(COLUMN_Age);
		String ClubID = res.getString(COLUMN_ClubID);
		String ClubName = res.getString(COLUMN_ClubName);
		String Seniority = res.getString(COLUMN_Seniority);
		String Intro = res.getString(COLUMN_Intro);
		String ACHV = res.getString(COLUMN_ACHV);
		String TeachACHV = res.getString(COLUMN_TeachACHV);
		String Verify = res.getString(COLUMN_Verify);
		String TeachLocation = res.getString(COLUMN_TeachLocation);
		String TeachCollege = res.getString(COLUMN_TeachCollege);
		String TeachAddress = res.getString(COLUMN_TeachAddress);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new Coach(UID,CoachID,CoachName,CoachHead,Age,ClubID,ClubName,Seniority,Intro,ACHV,TeachACHV,Verify,TeachLocation,TeachCollege,TeachAddress,Created_TS,Updated_TS);
	}

}
