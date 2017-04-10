package com.galaxy.ggolf.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.galaxy.ggolf.domain.PhoneCode;




public class PhoneCodeRowMapper implements RowMapper<PhoneCode> {

	public final static String COLUMN_UID = "UID";
	public final static String COLUMN_PhoneNum = "PhoneNum";
	public final static String COLUMN_DATETIME = "DATETIME";
	public final static String COLUMN_Code = "Code";
	

	@Override
	public PhoneCode mapRow(ResultSet res) throws SQLException {
		String uid = res.getString(COLUMN_UID);
		String phone = res.getString(COLUMN_PhoneNum);
		String datetime = res.getString(COLUMN_DATETIME);
		String code = res.getString(COLUMN_Code);
		return new PhoneCode(uid, phone,datetime, code);
	}

}
