package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.CourseTime;

public class CourseTimeRowMapper implements RowMapper<CourseTime> {
	private static final String COLUMN_CourseTimeID = "CourseTimeID";
	private static final String COLUMN_CourseID = "CourseID";
	private static final String COLUMN_CoachID = "CoachID";
	private static final String COLUMN_IsOpen = "IsOpen";
	private static final String COLUMN_OpenDate = "OpenDate";
	private static final String COLUMN_OpenTime = "OpenTime";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public CourseTime mapRow(ResultSet res) throws SQLException {
		String CourseTimeID = res.getString(COLUMN_CourseTimeID);
		String CourseID = res.getString(COLUMN_CourseID);
		String CoachID = res.getString(COLUMN_CoachID);
		String IsOpen = res.getString(COLUMN_IsOpen);
		String OpenDate = res.getString(COLUMN_OpenDate);
		String OpenTime = res.getString(COLUMN_OpenTime);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new CourseTime(CourseTimeID,CourseID,CoachID,IsOpen,OpenDate,OpenTime,Created_TS,Updated_TS);
	}
	
}
