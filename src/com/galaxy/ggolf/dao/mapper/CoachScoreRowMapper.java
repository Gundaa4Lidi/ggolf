package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.CoachScore;

public class CoachScoreRowMapper implements RowMapper<CoachScore> {
	public static final String COLUMN_UID = "UID";
	public static final String COLUMN_CoachID = "CoachID";
	public static final String COLUMN_Score = "Score";
	public static final String COLUMN_TeachNo = "TeachNo";
	public static final String COLUMN_Created_TS = "Created_TS";
	public static final String COLUMN_Updated_TS = "Updated_TS";


	@Override
	public CoachScore mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String CoachID = res.getString(COLUMN_CoachID);
		String Score = res.getString(COLUMN_Score);
		String TeachNo = res.getString(COLUMN_TeachNo);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new CoachScore(UID,CoachID,Score,TeachNo,Created_TS,Updated_TS);
	}

}
