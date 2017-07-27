package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.tools.ListUtil;

public class MessageRowMapper implements RowMapper<Message> {
    private static final String COLUMN_MsgID = "MsgID";
    private static final String COLUMN_SenderID = "SenderID";
    private static final String COLUMN_SenderName = "SenderName";
    private static final String COLUMN_SenderPhoto = "SenderPhoto";
    private static final String COLUMN_Title = "Title";
    private static final String COLUMN_Details = "Details";
    private static final String COLUMN_PhotoList = "PhotoList";
    private static final String COLUMN_Video = "Video";
    private static final String COLUMN_Period = "Period";
    private static final String COLUMN_Status = "Status";
    private static final String COLUMN_Type = "Type";
    private static final String COLUMN_ClubID = "ClubID";
    private static final String COLUMN_Club = "Club";
    private static final String COLUMN_Longitude = "Longitude";
    private static final String COLUMN_Latitude = "Latitude";
    private static final String COLUMN_Radius = "Radius";
    private static final String COLUMN_Site = "Site";
    private static final String COLUMN_ReleaseOrNot = "ReleaseOrNot";
    private static final String COLUMN_Created_TS = "Created_TS";
    private static final String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public Message mapRow(ResultSet res) throws SQLException {
		String MsgID = res.getString(COLUMN_MsgID);
		String SenderID = res.getString(COLUMN_SenderID);
		String SenderName = res.getString(COLUMN_SenderName);
		String SenderPhoto = res.getString(COLUMN_SenderPhoto);
		String Title = res.getString(COLUMN_Title);
		String Details = res.getString(COLUMN_Details);
		String photoList = res.getString(COLUMN_PhotoList);
		Collection<String> PhotoList = new ListUtil().StringToList(photoList,";");
		String Video = res.getString(COLUMN_Video);
		String Period = res.getString(COLUMN_Period);
		String Status = res.getString(COLUMN_Status);
		String Type = res.getString(COLUMN_Type);
		String ClubID = res.getString(COLUMN_ClubID);
		String Club = res.getString(COLUMN_Club);
		String Longitude = res.getString(COLUMN_Longitude);
		String Latitude = res.getString(COLUMN_Latitude);
		String Radius = res.getString(COLUMN_Radius);
		String Site = res.getString(COLUMN_Site);
		String ReleaseOrNot = res.getString(COLUMN_ReleaseOrNot);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new Message(MsgID,SenderID,SenderName,SenderPhoto,Title,Details,
				PhotoList,Video,Period,Status,Type,ClubID,Club,Longitude,Latitude,Radius,Site,ReleaseOrNot,Created_TS,Updated_TS,null);
	}

}
