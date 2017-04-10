package com.galaxy.ggolf.domain;

public class Online {
	private String UserID;
	private String OnlineOrNot;
	private String LocationOrNot;
	
	public Online(String userID, String onlineOrNot, String locationOrNot) {
		UserID = userID;
		OnlineOrNot = onlineOrNot;
		LocationOrNot = locationOrNot;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getOnlineOrNot() {
		return OnlineOrNot;
	}
	public void setOnlineOrNot(String onlineOrNot) {
		OnlineOrNot = onlineOrNot;
	}
	public String getLocationOrNot() {
		return LocationOrNot;
	}
	public void setLocationOrNot(String locationOrNot) {
		LocationOrNot = locationOrNot;
	}
	
	
}
