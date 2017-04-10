package com.galaxy.ggolf.domain;

public class PhoneCode {

	private String uid;  //默认ID

	private String phone;  //手机号码

	private String code;  //验证码
	
	private String datetime;  //创建日期

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public PhoneCode(String uid, String phone,String datetime, String code) {
		super();
		this.uid = uid;
		this.phone = phone;
		this.datetime = datetime;
		this.code = code;
		
	}

	public PhoneCode(String phone, String code) {
		super();
		this.phone = phone;
		this.code = code;
	}

	

}
