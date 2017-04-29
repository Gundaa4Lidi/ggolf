package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.ClubServe;

public class ClubServeRowMapper implements RowMapper<ClubServe> {
	public static final String COLUMN_ClubserveID = "ClubserveID";
	public static final String COLUMN_ClubID = "ClubID";
	public static final String COLUMN_ClubName = "ClubName";
	public static final String COLUMN_Name = "Name";
	public static final String COLUMN_CancelExplain = "CancelExplain";
	public static final String COLUMN_ReserveExplain = "ReserveExplain";
	public static final String COLUMN_ServiceExplain = "ServiceExplain";
	public static final String COLUMN_MaxDay = "MaxDay";
	public static final String COLUMN_Created_TS = "Created_TS";
	public static final String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public ClubServe mapRow(ResultSet res) throws SQLException {
		String ClubserveID = res.getString(COLUMN_ClubserveID);
		String ClubID = res.getString(COLUMN_ClubID);
		String ClubName = res.getString(COLUMN_ClubName);
		String Name = res.getString(COLUMN_Name);
		String CancelExplain = res.getString(COLUMN_CancelExplain);
		String ReserveExplain = res.getString(COLUMN_ReserveExplain);
		String ServiceExplain = res.getString(COLUMN_ServiceExplain);
		String MaxDay = res.getString(COLUMN_MaxDay);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new ClubServe(ClubserveID,ClubID,ClubName,Name,CancelExplain,ReserveExplain,ServiceExplain,MaxDay,Created_TS,Updated_TS);
	}

}
