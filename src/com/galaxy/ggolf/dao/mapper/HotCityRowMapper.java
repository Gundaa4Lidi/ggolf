package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.HotCity;

public class HotCityRowMapper implements RowMapper<HotCity> {
	private static final String COLUMN_UID = "UID";
	private static final String COLUMN_Province = "Province";
	private static final String COLUMN_City = "City";
	private static final String COLUMN_IsHot = "IsHot";
	private static final String COLUMN_Updated_TS = "Updated_TS";

	
	@Override
	public HotCity mapRow(ResultSet res) throws SQLException {
		String UID = res.getString(COLUMN_UID);
		String Province = res.getString(COLUMN_Province);
		String City = res.getString(COLUMN_City);
		String IsHot = res.getString(COLUMN_IsHot);
		String Updated_TS = res.getString(COLUMN_Updated_TS);
		return new HotCity(UID,Province,City,IsHot,Updated_TS);
	}

}
