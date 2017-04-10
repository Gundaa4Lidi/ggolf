package com.galaxy.ggolf.domain;

public class Likes {
	private String UID;
	private String UserID;
	private String ThemeID;
	private String Type;
	private String Status;
	private String Created_TS;
	public Likes(String uID, String userID, String themeID, String type, String status, String created_TS) {
		UID = uID;
		UserID = userID;
		ThemeID = themeID;
		Type = type;
		Status = status;
		Created_TS = created_TS;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getThemeID() {
		return ThemeID;
	}
	public void setThemeID(String themeID) {
		ThemeID = themeID;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	
	
	
}
