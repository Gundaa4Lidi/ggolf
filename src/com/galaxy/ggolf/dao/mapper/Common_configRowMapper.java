package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Common_config;



public class Common_configRowMapper implements RowMapper<Common_config> {

	public final static String COLUMN_KEY = "KEY";
	public final static String COLUMN_Describe = "Describe";
	public final static String COLUMN_VALUE = "VALUE";


	@Override
	public Common_config mapRow(ResultSet res) throws SQLException {
		String key = res.getString(COLUMN_KEY);
		String Describe = res.getString(COLUMN_Describe);
		String value = res.getString(COLUMN_VALUE);
		return new Common_config(key,Describe, value);
	}

}
