package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.DTGroup;

public class DTGroupRowMapper implements RowMapper<DTGroup> {
	private static final String COLUMN_Created_TS = "Created_TS";
	@Override
	public DTGroup mapRow(ResultSet res) throws SQLException {
		String Created_TS = res.getString(COLUMN_Created_TS);
		// TODO Auto-generated method stub
		return new DTGroup(Created_TS);
	}

}
