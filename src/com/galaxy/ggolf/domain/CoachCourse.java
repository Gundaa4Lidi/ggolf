package com.galaxy.ggolf.domain;

public class CoachCourse {
	private String CourseID;
	private String CoachID;
	private String CoachName;
	private String CoachHead;
	private String CoachPhone;
	private String Title;
	private String Price;
	private String Verify;
	private String Valid;
	private String IsOpen;
	private String MaxPeople;
	private String IsBatch;
	private String ClassHour;
	private String ContainExplain;
	private String IsVideo;
	private String IsRead;
	private String Created_TS;
	private String Updated_TS;
	private CourseVideo courseVideo;
	private CourseTime courseTime;
	
	public CoachCourse(String courseID, String coachID,String title, String price, String verify, String valid,
			String isOpen, String maxPeople, String isBatch,String classHour, String containExplain,
			String isVideo, String isRead, String created_TS, String updated_TS) {
		CourseID = courseID;
		CoachID = coachID;
		Title = title;
		Price = price;
		Verify = verify;
		Valid = valid;
		IsOpen = isOpen;
		MaxPeople = maxPeople;
		IsBatch = isBatch;
		ClassHour = classHour;
		ContainExplain = containExplain;
		IsVideo = isVideo;
		IsRead = isRead;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
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
	public String getCoachName() {
		return CoachName;
	}
	public void setCoachName(String coachName) {
		CoachName = coachName;
	}
	public String getCoachHead() {
		return CoachHead;
	}
	public void setCoachHead(String coachHead) {
		CoachHead = coachHead;
	}
	public String getCoachPhone() {
		return CoachPhone;
	}
	public void setCoachPhone(String coachPhone) {
		CoachPhone = coachPhone;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getVerify() {
		return Verify;
	}
	public void setVerify(String verify) {
		Verify = verify;
	}
	public String getValid() {
		return Valid;
	}
	public void setValid(String valid) {
		Valid = valid;
	}
	public String getIsOpen() {
		return IsOpen;
	}
	public void setIsOpen(String isOpen) {
		IsOpen = isOpen;
	}
	public String getMaxPeople() {
		return MaxPeople;
	}
	public void setMaxPeople(String maxPeople) {
		MaxPeople = maxPeople;
	}
	public String getContainExplain() {
		return ContainExplain;
	}
	public void setContainExplain(String containExplain) {
		ContainExplain = containExplain;
	}
	public String getIsVideo() {
		return IsVideo;
	}
	public void setIsVideo(String isVideo) {
		IsVideo = isVideo;
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
	public CourseVideo getCourseVideo() {
		return courseVideo;
	}
	public void setCourseVideo(CourseVideo courseVideo) {
		this.courseVideo = courseVideo;
	}
	public String getIsBatch() {
		return IsBatch;
	}
	public void setIsBatch(String isBatch) {
		IsBatch = isBatch;
	}
	public String getClassHour() {
		return ClassHour;
	}
	public void setClassHour(String classHour) {
		ClassHour = classHour;
	}
	public CourseTime getCourseTime() {
		return courseTime;
	}
	public void setCourseTime(CourseTime courseTime) {
		this.courseTime = courseTime;
	}
	public String getIsRead() {
		return IsRead;
	}
	public void setIsRead(String isRead) {
		IsRead = isRead;
	}
	
	
	
}
