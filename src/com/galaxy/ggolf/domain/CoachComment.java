package com.galaxy.ggolf.domain;

public class CoachComment {
	private String UID;
	private String CoachID;
	private String UserID;
	private String UserHead;
	private String UserName;
	private String Score;
	private String Content;
	private String Created_TS;
	public CoachComment(String uID, String coachID, String userID, String userHead, String userName, String score,
			String content, String created_TS) {
		UID = uID;
		CoachID = coachID;
		UserID = userID;
		UserHead = userHead;
		UserName = userName;
		Score = score;
		Content = content;
		Created_TS = created_TS;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getCoachID() {
		return CoachID;
	}
	public void setCoachID(String coachID) {
		CoachID = coachID;
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
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	
	
}
