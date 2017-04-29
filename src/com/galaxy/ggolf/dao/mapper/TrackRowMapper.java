package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.galaxy.ggolf.domain.Track;
import com.galaxy.ggolf.tools.ListUtil;

public class TrackRowMapper implements RowMapper<Track>{
   private static final String  COLUMN_UserId="UserId";
   private static final String  COLUMN_TrackId="TrackId";
   private static final String  COLUMN_Grade="Grade";
   private static final String  COLUMN_Created_TS="Created_TS";
   private static final String  COLUMN_PhotoList="PhotoList";
   private static final String  COLUMN_Content="Content";
   private static final String  COLUMN_ClubName="ClubName";
   private static final String COLUMN_PlayerName="PlayerName";
   private static final String COLUMN_SiteOnePAR="SiteOnePAR";
   private static final String COLUMN_SiteTwoPAR="SiteTwoPAR";
   private static final String COLUMN_ScoresOne="ScoresOne";
   private static final String COLUMN_ScoresTwo="ScoresTwo";
   private static final String COLUMN_PuttersOne="PuttersOne";
   private static final String COLUMN_PuttersTwo="PuttersTwo";
   @Override
	public Track mapRow(ResultSet res) throws SQLException {
		String UserId=res.getString(COLUMN_UserId);
		String TrackId=res.getString(COLUMN_TrackId);
		String Grade=res.getString(COLUMN_Grade);
		String Created_TS=res.getString(COLUMN_Created_TS);
		String photoList=res.getString(COLUMN_PhotoList);
		Collection<String> PhotoList=new ListUtil().StringToList(photoList);
		String Content=res.getString(COLUMN_Content);
		String ClubName=res.getString(COLUMN_ClubName);
		String PlayerName=res.getString(COLUMN_PlayerName);
		String SiteOnePAR=res.getString(COLUMN_SiteOnePAR);
	    String SiteTwoPAR=res.getString(COLUMN_SiteTwoPAR);
	    String ScoresOne=res.getString(COLUMN_ScoresOne);
	    String ScoresTwo=res.getString(COLUMN_ScoresTwo);
	    String PuttersOne=res.getString(COLUMN_PuttersOne);
	    String PuttersTwo=res.getString(COLUMN_PuttersTwo);
		return new Track(UserId,TrackId,Grade,Created_TS,PhotoList,Content,ClubName,PlayerName,SiteOnePAR,SiteTwoPAR,ScoresOne,ScoresTwo,PuttersOne,PuttersTwo);
	}

}
