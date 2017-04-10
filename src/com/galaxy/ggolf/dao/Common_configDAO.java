package com.galaxy.ggolf.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.Common_configRowMapper;
import com.galaxy.ggolf.domain.Common_config;

public class Common_configDAO extends GenericDAO<Common_config> {

	public Common_configDAO() {
		super(new Common_configRowMapper());
	}



	public Collection<Common_config> getAllFee() throws Exception {
		String sql = "SELECT * FROM `Common_config` ";
		return super.executeQuery(sql);
	}

	public boolean createFee(Common_config Common_config) {
		String sql = "INSERT INTO Common_config (`KEY`,`Describe`,`VALUE`) VALUES ('"
				+ Common_config.getKEY()
				+ "','"
				+ Common_config.getDescribe() 
				+ "','"
				+ Common_config.getVALUE()+"')";
		return super.executeUpdate(sql);
	}
	
	public Common_config  getCommon_cofig(String key) {
		String sql = "select * from Common_config where `KEY` like '" + key + "'";
		Collection<Common_config> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return (Common_config) result.toArray()[0];
		} else {
			return null;
		}
	} 



	public boolean updateFee(String key, String value) throws Exception {
		String sql = "update Common_config set VALUE = ? where `KEY` like ? ";
		ArrayList<String> params = new ArrayList<String>();
		params.add(value);
		params.add(key);
		return super.executeUpdateWithStatement(sql, params);
	}



}
