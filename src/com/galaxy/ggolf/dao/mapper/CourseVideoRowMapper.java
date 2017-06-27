package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.galaxy.ggolf.domain.CourseVideo;
import com.galaxy.ggolf.dto.Activity;
import com.galaxy.ggolf.dto.LiveEvent;

public class CourseVideoRowMapper implements RowMapper<CourseVideo> {
	public static final String COLUMN_UID = "UID";
	public static final String COLUMN_CreatorID = "CreatorID";
	public static final String COLUMN_CourseID = "CourseID";
	public static final String COLUMN_RoomID = "RoomID";
	public static final String COLUMN_Password = "Password";
	public static final String COLUMN_RoomName = "RoomName";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";
	private static final String COLUMN_LiveEvent = "LiveEvent";

	@Override
	public CourseVideo mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String CreatorID = res.getString(COLUMN_CreatorID);
		String CourseID = res.getString(COLUMN_CourseID);
		String RoomID = res.getString(COLUMN_RoomID);
		String Password = res.getString(COLUMN_Password);
		String RoomName = res.getString(COLUMN_RoomName);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		ArrayList<LiveEvent> LiveEvents  = new ArrayList<LiveEvent>();
		String[] liveEvents = res.getString(COLUMN_LiveEvent).split("\\|");
		for(String liveEvent:liveEvents){
			LiveEvent a = new LiveEvent();
			String[] properties = liveEvent.split(",");
			a.setTime(properties[0]);
			a.setAction(properties[1]);
			a.setUserID(properties[2]);
			LiveEvents.add(a);
		}
		return new CourseVideo(UID,CreatorID,CourseID,RoomID,Password,RoomName,Created_TS,Updated_TS,LiveEvents);
	}

}
