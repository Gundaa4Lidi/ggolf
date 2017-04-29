package com.galaxy.ggolf.domain;

public class CoachScore {
	
	private String UID;
	private String CoachID;
	private String Score;
	private String TeachNo;
	private String Created_TS;
	private String Updated_TS;
	
	public CoachScore() {
		// TODO Auto-generated constructor stub
	}
	public CoachScore(String uID, String coachID, String score, String teachNo, String created_TS, String updated_TS) {
		UID = uID;
		CoachID = coachID;
		Score = score;
		TeachNo = teachNo;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
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
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	public String getTeachNo() {
		return TeachNo;
	}
	public void setTeachNo(String teachNo) {
		TeachNo = teachNo;
	}
	public String getCreated_TS() {
		return Created_TS;
	}
	public void setCreated_TS(String created_TS) {
		Created_TS = created_TS;
	}
	public String getUpdated_TS() {
		return Updated_TS;
	}
	public void setUpdated_TS(String updated_TS) {
		Updated_TS = updated_TS;
	}
	
	
	
}
