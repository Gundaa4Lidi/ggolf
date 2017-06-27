package com.galaxy.ggolf.domain;

import java.util.Collection;

import com.galaxy.ggolf.dto.Activity;

public class CourseOrder {
	private String CourseOrderID;
	private String CoachID;
	private String CoachName;
	private String CourseID;
	private String CourseTitle;
	private String UserID;
	private String UserName;
	private String State;
	private String Type;
	private String TeachingMethod;
	private String ClassHour;
	private String DownPayment;
	private String StartDateTime;
	private String Tel;
	private String IsBatch;
	private String IsTaught;
	private String IsRead;
	private String Marks;
	private String ChargeID;
	private String Refund;
	private String Description;
	private String ServiceExplain;
	private String Created_TS;
	private String Updated_TS;
	private Collection<Activity> Activity;
	
	public CourseOrder(String courseOrderID, String coachID, String coachName, String courseID, String courseTitle,
			String userID, String userName, String state, String type, String teachingMethod, String classHour,
			String downPayment, String startDateTime, String tel, String isBatch, String isTaught, String isRead,
			String marks, String chargeID, String refund, String description, String serviceExplain, String created_TS, String updated_TS,
			Collection<Activity> activity) {
		CourseOrderID = courseOrderID;
		CoachID = coachID;
		CoachName = coachName;
		CourseID = courseID;
		CourseTitle = courseTitle;
		UserID = userID;
		UserName = userName;
		State = state;
		Type = type;
		TeachingMethod = teachingMethod;
		ClassHour = classHour;
		DownPayment = downPayment;
		StartDateTime = startDateTime;
		Tel = tel;
		IsBatch = isBatch;
		IsTaught = isTaught;
		IsRead = isRead;
		Marks = marks;
		ChargeID = chargeID;
		Refund = refund;
		Description = description;
		ServiceExplain = serviceExplain;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
		Activity = activity;
	}
	
	public String getCourseOrderID() {
		return CourseOrderID;
	}
	public void setCourseOrderID(String courseOrderID) {
		CourseOrderID = courseOrderID;
	}
	public String getCoachID() {
		return CoachID;
	}
	public void setCoachID(String coachID) {
		CoachID = coachID;
	}
	public String getCoachName() {
		return CoachName;
	}
	public void setCoachName(String coachName) {
		CoachName = coachName;
	}
	public String getCourseID() {
		return CourseID;
	}
	public void setCourseID(String courseID) {
		CourseID = courseID;
	}
	public String getCourseTitle() {
		return CourseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		CourseTitle = courseTitle;
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
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getTeachingMethod() {
		return TeachingMethod;
	}
	public void setTeachingMethod(String teachingMethod) {
		TeachingMethod = teachingMethod;
	}
	public String getClassHour() {
		return ClassHour;
	}
	public void setClassHour(String classHour) {
		ClassHour = classHour;
	}
	public String getDownPayment() {
		return DownPayment;
	}
	public void setDownPayment(String downPayment) {
		DownPayment = downPayment;
	}
	public String getStartDateTime() {
		return StartDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		StartDateTime = startDateTime;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public String getIsBatch() {
		return IsBatch;
	}
	public void setIsBatch(String isBatch) {
		IsBatch = isBatch;
	}
	public String getIsTaught() {
		return IsTaught;
	}
	public void setIsTaught(String isTaught) {
		IsTaught = isTaught;
	}
	public String getIsRead() {
		return IsRead;
	}
	public void setIsRead(String isRead) {
		IsRead = isRead;
	}
	public String getMarks() {
		return Marks;
	}
	public void setMarks(String marks) {
		Marks = marks;
	}
	public String getChargeID() {
		return ChargeID;
	}
	public void setChargeID(String chargeID) {
		ChargeID = chargeID;
	}
	public String getRefund() {
		return Refund;
	}
	public void setRefund(String refund) {
		Refund = refund;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getServiceExplain() {
		return ServiceExplain;
	}
	public void setServiceExplain(String serviceExplain) {
		ServiceExplain = serviceExplain;
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
	public Collection<Activity> getActivity() {
		return Activity;
	}
	public void setActivity(Collection<Activity> activity) {
		Activity = activity;
	}
	
	
	
	
}
