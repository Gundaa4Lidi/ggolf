package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

	public T mapRow(ResultSet res) throws SQLException;

}
