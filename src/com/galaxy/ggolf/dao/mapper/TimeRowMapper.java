package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.Time;

public class TimeRowMapper implements RowMapper<Time>{
    public static final String COLUMN_Screated_TS="Screated_TS";
    public static final String COLUMN_Tcreated_TS="Tcreated_TS";
    @Override
    public Time mapRow(ResultSet res) throws SQLException {
	String Screated_TS=res.getString(COLUMN_Screated_TS);
	String Tcreated_TS=res.getString(COLUMN_Tcreated_TS);
	return new Time(Screated_TS,Tcreated_TS);
    }
    
    
}
