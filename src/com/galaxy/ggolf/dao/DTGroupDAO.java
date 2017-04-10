package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.DTGroupRowMapper;
import com.galaxy.ggolf.domain.DTGroup;

public class DTGroupDAO extends GenericDAO<DTGroup> {

	public DTGroupDAO() {
		super(new DTGroupRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public Collection<DTGroup> getDateGroup(String sqlString,String table){
		String sql = "select Created_TS from '"+table+"' where DeletedFlag is null "+sqlString+" group by date_format(Created_TS,'%Y-%m-%d') order by Created_TS desc";
		return super.executeQuery(sql);
	}
}
