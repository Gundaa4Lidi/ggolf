package com.galaxy.ggolf.dto;

public class NewOrderData {
	private String UserID;
	private String UserName;
	private String UserPhoto;
	private String Type;
	private String ThemeID;
	private String IsRead;
	private String Created_TS;
	
	public NewOrderData() {
		// TODO Auto-generated constructor stub
	}
	
	public NewOrderData(String userID, String userName, String userPhoto, String type, String themeID, String isRead,
			String created_TS) {
		UserID = userID;
		UserName = userName;
		UserPhoto = userPhoto;
		Type = type;
		ThemeID = themeID;
		IsRead = isRead;
		Created_TS = created_TS;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPhoto() {
		return UserPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		UserPhoto = userPhoto;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getThemeID() {
		return ThemeID;
	}

	public void setThemeID(String themeID) {
		ThemeID = themeID;
	}

	public String getIsRead() {
		return IsRead;
	}

	public void setIsRead(String isRead) {
		IsRead = isRead;
	}

	public String getCreated_TS() {
		return Created_TS;
	}

	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	
	
	
	
}
