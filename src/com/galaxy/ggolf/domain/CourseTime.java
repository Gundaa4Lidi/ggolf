package com.galaxy.ggolf.domain;

public class CourseTime {
	private String CourseTimeID;
	private String CourseID;
	private String CoachID;
	private String IsOpen;
	private String OpenDate;
	private String OpenTime;
	private String Created_TS;
	private String Updated_TS;
	
	public CourseTime(String courseTimeID, String courseID, String coachID, String isOpen, String openDate,
			String openTime, String created_TS, String updated_TS) {
		CourseTimeID = courseTimeID;
		CourseID = courseID;
		CoachID = coachID;
		IsOpen = isOpen;
		OpenDate = openDate;
		OpenTime = openTime;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
	}
	
	public String getCourseTimeID() {
		return CourseTimeID;
	}
	public void setCourseTimeID(String courseTimeID) {
		CourseTimeID = courseTimeID;
	}
	public String getCourseID() {
		return CourseID;
	}
	public void setCourseID(String courseID) {
		CourseID = courseID;
	}
	public String getCoachID() {
		return CoachID;
	}
	public void setCoachID(String coachID) {
		CoachID = coachID;
	}
	public String getIsOpen() {
		return IsOpen;
	}
	public void setIsOpen(String isOpen) {
		IsOpen = isOpen;
	}
	public String getOpenDate() {
		return OpenDate;
	}
	public void setOpenDate(String openDate) {
		OpenDate = openDate;
	}
	public String getOpenTime() {
		return OpenTime;
	}
	public void setOpenTime(String openTime) {
		OpenTime = openTime;
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
