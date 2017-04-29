package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.galaxy.ggolf.domain.ClubFairway;
import com.galaxy.ggolf.tools.ListUtil;

public class ClubFairwayRowMapper implements RowMapper<ClubFairway> {
	
	public static final String COLUMN_UID = "UID";
	public static final String COLUMN_ClubID = "ClubID";
	public static final String COLUMN_HoleNum = "HoleNum";
	public static final String COLUMN_FairwayName = "FairwayName";
	public static final String COLUMN_Updated_TS = "Updated_TS";
	public static final String COLUMN_Photo = "Photo";
	public static final String COLUMN_Par = "Par";


	@Override
	public ClubFairway mapRow(ResultSet res) throws SQLException {
	    String UID = res.getString(COLUMN_UID);
	    String ClubID = res.getString(COLUMN_ClubID);
	    String HoleNum = res.getString(COLUMN_HoleNum);
	    String FairwayName = res.getString(COLUMN_FairwayName);
	    String Updated_TS = res.getString(COLUMN_Updated_TS);
	    String Photo = res.getString(COLUMN_Photo);
	    String Par = res.getString(COLUMN_Par);
	    Collection<String> photos = new ListUtil().StringToList(Photo);
		return new ClubFairway(UID, ClubID, HoleNum, FairwayName, Updated_TS, Par, photos);
	}

}
