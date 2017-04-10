package com.galaxy.ggolf.dto;

public class Location {
	private String UserID;
	private String Location;
	private String Result;
	public Location(String userID, String location, String result) {
		UserID = userID;
		Location = location;
		Result = result;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	
	
	
	
}
