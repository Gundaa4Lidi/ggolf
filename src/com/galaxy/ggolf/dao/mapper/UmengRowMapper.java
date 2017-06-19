package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Umeng;

public class UmengRowMapper implements RowMapper<Umeng> {

	private final static String COLUMN_UMID = "UMID";
	private final static String COLUMN_UserID = "UserID";
	private final static String COLUMN_Umeng_Token = "Umeng_Token";
	private final static String COLUMN_Created_TS = "Created_TS";
	private final static String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public Umeng mapRow(ResultSet res) throws SQLException {
		String UMID = res.getString(COLUMN_UMID);
		String UserID = res.getString(COLUMN_UserID);
		String Umeng_Token = res.getString(COLUMN_Umeng_Token);
		String Created_TS = res.getString(COLUMN_Created_TS);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		

		return new Umeng(UMID, UserID, Umeng_Token, Created_TS, Updated_TS);
	}

}
