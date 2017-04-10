package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Collect;

public class CollectRowMapper implements RowMapper<Collect> {
	private static final String COLUMN_UID = "UID";
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_ThemeID = "ThemeID";
	private static final String COLUMN_Type = "Type";
	private static final String COLUMN_Status = "Status";
	private static final String COLUMN_Created_TS = "Created_TS";
	@Override
	public Collect mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String UserID = res.getString(COLUMN_UserID);
		String ThemeID = res.getString(COLUMN_ThemeID);
		String Type = res.getString(COLUMN_Type);
		String Status = res.getString(COLUMN_Status);
		String Created_TS = res.getString(COLUMN_Created_TS);
		return new Collect(UID, UserID, ThemeID, Type, Status, Created_TS);
	}

}
