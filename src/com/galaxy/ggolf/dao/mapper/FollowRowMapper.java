package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Follow;

public class FollowRowMapper implements RowMapper<Follow> {
	private static final String COLUMN_UID = "UID";
	private static final String COLUMN_UserID = "UserID";
	private static final String COLUMN_FenID = "FenID";
	private static final String COLUMN_Relation = "Relation";
	private static final String COLUMN_Remark = "Remark";
	private static final String COLUMN_Status = "Status";
	private static final String COLUMN_Created_TS = "Created_TS";
	private static final String COLUMN_Updated_TS = "Updated_TS";

	@Override
	public Follow mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String UserID = res.getString(COLUMN_UserID);
	    String FenID = res.getString(COLUMN_FenID);
	    String Relation = res.getString(COLUMN_Relation);
	    String Remark = res.getString(COLUMN_Remark);
	    String Status = res.getString(COLUMN_Status);
	    String Created_TS = res.getString(COLUMN_Created_TS);
	    String Updated_TS = res.getString(COLUMN_Updated_TS);
	    return new Follow(UID,UserID,FenID,Relation,Remark,Status,Created_TS,Updated_TS);
	}

}
