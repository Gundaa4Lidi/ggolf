package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.CourseVideo;

public class CourseVideoRowMapper implements RowMapper<CourseVideo> {
	public static final String COLUMN_UID = "UID";
	public static final String COLUMN_CreatorID = "CreatorID";
	public static final String COLUMN_CourseID = "CourseID";
	public static final String COLUMN_RoomID = "RoomID";
	public static final String COLUMN_Password = "Password";
	public static final String COLUMN_RoomName = "RoomName";
	@Override
	public CourseVideo mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String CreatorID = res.getString(COLUMN_CreatorID);
		String CourseID = res.getString(COLUMN_CourseID);
		String RoomID = res.getString(COLUMN_RoomID);
		String Password = res.getString(COLUMN_Password);
		String RoomName = res.getString(COLUMN_RoomName);
		return new CourseVideo(UID,CreatorID,CourseID,RoomID,Password,RoomName);
	}

}
