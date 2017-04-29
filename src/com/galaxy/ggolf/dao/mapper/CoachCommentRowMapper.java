package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.CoachComment;

public class CoachCommentRowMapper implements RowMapper<CoachComment> {
	private static final String COLUMN_UID = "UID";
	private static final String COLUMN_CoachID = "CoachID";
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_UserHead = "UserHead";
	private static final String COLUMN_UserName = "UserName";
	private static final String COLUMN_Score = "Score";
	private static final String COLUMN_Content = "Content";
	private static final String COLUMN_Created_TS = "Created_TS";
	@Override
	public CoachComment mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String CoachID = res.getString(COLUMN_CoachID);
		String UserID = res.getString(COLUMN_UserID);
		String UserHead = res.getString(COLUMN_UserHead);
		String UserName = res.getString(COLUMN_UserName);
		String Score = res.getString(COLUMN_Score);
		String Content = res.getString(COLUMN_Content);
		String Created_TS = res.getString(COLUMN_Created_TS);
		return new CoachComment(UID,CoachID,UserID,UserHead,UserName,Score,Content,Created_TS);
	}

}
