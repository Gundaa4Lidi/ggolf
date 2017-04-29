package com.galaxy.ggolf.domain;

public class HotCity {
	
	private String UID;
	private String Province;
	private String City;
	private String IsHot;
	private String Updated_TS;
	
	public HotCity(String uID, String province, String city, String isHot, String updated_TS) {
		UID = uID;
		Province = province;
		City = city;
		IsHot = isHot;
		Updated_TS = updated_TS;
	}
	
	public HotCity(String Province){
		this.Province = Province;
	}
	
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getIsHot() {
		return IsHot;
	}
	public void setIsHot(String isHot) {
		IsHot = isHot;
	}
	public String getUpdated_TS() {
		return Updated_TS;
	}
	public void setUpdated_TS(String updated_TS) {
		Updated_TS = updated_TS;
	}
	
	
	

}
