package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.OtherDate;

public class OtherDateRowMapper implements RowMapper<OtherDate> {
	private static final String COLUMN_UID = "UID";
	private static final String COLUMN_ClubID = "ClubID";
	private static final String COLUMN_ClubserveID = "ClubserveID";
	private static final String COLUMN_DateTime = "DateTime";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";
	@Override
	public OtherDate mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String ClubID = res.getString(COLUMN_ClubID);
		String ClubserveID = res.getString(COLUMN_ClubserveID);
		String DateTime = res.getString(COLUMN_DateTime);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new OtherDate(UID,ClubID,ClubserveID,DateTime,Created_TS,Updated_TS);
	}

}
