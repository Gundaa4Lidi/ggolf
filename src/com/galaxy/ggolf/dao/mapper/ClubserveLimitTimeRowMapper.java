package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.ClubserveLimitTime;

public class ClubserveLimitTimeRowMapper implements RowMapper<ClubserveLimitTime> {

	private static final String COLUMN_ClubserveLimitTimeID = "ClubserveLimitTimeID";
	private static final String COLUMN_ClubserveID = "ClubserveID";
	private static final String COLUMN_Price = "Price";
	private static final String COLUMN_StartTime = "StartTime";
	private static final String COLUMN_EndTime = "EndTime";
	private static final String COLUMN_IsUsed = "IsUsed";
	private static final String COLUMN_Count = "Count";
	private static final String COLUMN_Date = "Date";
	private static final String COLUMN_ServiceExplain = "ServiceExplain";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public ClubserveLimitTime mapRow(ResultSet res) throws SQLException {

		String ClubserveLimitTimeID = res.getString(COLUMN_ClubserveLimitTimeID);
		String ClubserveID = res.getString(COLUMN_ClubserveID);
		String Price = res.getString(COLUMN_Price);
		String StartTime = res.getString(COLUMN_StartTime);
		String EndTime = res.getString(COLUMN_EndTime);
		String IsUsed = res.getString(COLUMN_IsUsed);
		String Count = res.getString(COLUMN_Count);
		String Date = res.getString(COLUMN_Date);
		String ServiceExplain = res.getString(COLUMN_ServiceExplain);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new ClubserveLimitTime(ClubserveLimitTimeID,ClubserveID,Price,StartTime,EndTime,IsUsed,Count,Date,ServiceExplain,Created_TS,Updated_TS);
	}

}
