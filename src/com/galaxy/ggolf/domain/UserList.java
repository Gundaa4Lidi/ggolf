package com.galaxy.ggolf.domain;

public class UserList {
	private String UserID;
	
	public UserList(String userID) {
		UserID = userID;
	}
	
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	
}
