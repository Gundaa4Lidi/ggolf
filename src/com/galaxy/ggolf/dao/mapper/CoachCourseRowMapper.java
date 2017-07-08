package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.CoachCourse;

public class CoachCourseRowMapper implements RowMapper<CoachCourse> {
	private static final String COLUMN_CourseID = "CourseID";
	private static final String COLUMN_CoachID = "CoachID";
//	private static final String COLUMN_CoachName = "CoachName";
//	private static final String COLUMN_CoachHead = "CoachHead";
//	private static final String COLUMN_CoachPhone = "CoachPhone";
	private static final String COLUMN_Title = "Title";
	private static final String COLUMN_Price = "Price";
	private static final String COLUMN_Verify = "Verify";
	private static final String COLUMN_Valid = "Valid";
	private static final String COLUMN_IsOpen = "IsOpen";
	private static final String COLUMN_MaxPeople = "MaxPeople";
	private static final String COLUMN_IsBatch = "IsBatch";
	private static final String COLUMN_ClassHour = "ClassHour";
	private static final String COLUMN_ContainExplain = "ContainExplain";
	private static final String COLUMN_IsVideo = "IsVideo";
	private static final String COLUMN_IsRead = "IsRead";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public CoachCourse mapRow(ResultSet res) throws SQLException {
		String CourseID = res.getString(COLUMN_CourseID);
		String CoachID = res.getString(COLUMN_CoachID);
//		String CoachName = res.getString(COLUMN_CoachName);
//		String CoachHead = res.getString(COLUMN_CoachHead);
//		String CoachPhone = res.getString(COLUMN_CoachPhone);
		String Title = res.getString(COLUMN_Title);
		String Price = res.getString(COLUMN_Price);
		String Verify = res.getString(COLUMN_Verify);
		String Valid = res.getString(COLUMN_Valid);
		String IsOpen = res.getString(COLUMN_IsOpen);
		String MaxPeople = res.getString(COLUMN_MaxPeople);
		String IsBatch = res.getString(COLUMN_IsBatch);
		String ClassHour = res.getString(COLUMN_ClassHour);
		String ContainExplain = res.getString(COLUMN_ContainExplain);
		String IsVideo = res.getString(COLUMN_IsVideo);
		String IsRead = res.getString(COLUMN_IsRead);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new CoachCourse(CourseID,CoachID,Title,Price,Verify,Valid,IsOpen,MaxPeople,IsBatch,ClassHour,ContainExplain,IsVideo,IsRead,Created_TS,Updated_TS);


	}

}
