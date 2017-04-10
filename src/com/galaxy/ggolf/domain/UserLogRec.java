package com.galaxy.ggolf.domain;

public class UserLogRec {
	private String UserID; //默认ID
	private String Login_Place; //登录地点
	private String Created_TS; //创建日期
	private String Longitude; //经度
	private String latitude; //纬度
	
	
	public UserLogRec(String userID, String login_Place, String created_TS, String longitude, String latitude) {
		super();
		this.UserID = userID;
		this.Login_Place = login_Place;
		this.Created_TS = created_TS;
		this.Longitude = longitude;
		this.latitude = latitude;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getLogin_Place() {
		return Login_Place;
	}
	public void setLogin_Place(String login_Place) {
		Login_Place = login_Place;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	

	

}
