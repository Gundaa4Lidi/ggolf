package com.galaxy.ggolf.dao;

import java.text.SimpleDateFormat;
import java.util.Collection;




import java.util.Date;

import com.galaxy.ggolf.dao.mapper.PhoneCodeRowMapper;
import com.galaxy.ggolf.domain.PhoneCode;


public class PhoneCodeDAO extends GenericDAO<PhoneCode> {

	public PhoneCodeDAO() {
		super(new PhoneCodeRowMapper());
	}

	

	public boolean createPhoneCode(String phone,String code) {
		Date now = new Date();
		String dt = new String(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		String sql = "INSERT INTO phonecode (PhoneNum,DATETIME,Code) VALUES ('"
				+ phone + "','"+dt+"','" + code + "')";
		return super.executeUpdate(sql);
	}
	
	public boolean updatecode(String phone,String code ){
		Date now = new Date();
		String dt = new String(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		String sql = "update phonecode set Code = '"+code+"',and set DATETIME = '"+dt+"' where PhoneNum ='" + phone + "'";
		return super.executeUpdate(sql);
		
	}
	public PhoneCode getCodeByPhone(String phone) {
		String sql = "select * from phonecode where PhoneNum ='" + phone + "' order by  UID desc";
		Collection<PhoneCode> result = super.executeQuery(sql);
		if (result.size() > 0) {
			return (PhoneCode) result.toArray()[0];
		} else {
			return null;
		}
	}

}
