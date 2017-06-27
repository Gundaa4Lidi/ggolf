package com.galaxy.ggolf.dto;

public class LiveEvent {
	private String Time;
	private String Action;
	private String UserID;
	private String UserHead;
	
	public LiveEvent(String time, String action, String userID) {
		Time = time;
		Action = action;
		UserID = userID;
	}

	public LiveEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserHead() {
		return UserHead;
	}
	public void setUserHead(String userHead) {
		UserHead = userHead;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	
	
	
	
}
