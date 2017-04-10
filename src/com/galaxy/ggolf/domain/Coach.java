package com.galaxy.ggolf.domain;

public class Coach {
	private String CoachID;
	private String UserID;
	private String UserName;
	private String UserHead;
	private String Age;
	private String College;
	private String Seniority;
	private String Intro;
	private String ACHV;
	private String TeachACHV;
	private String Verify;
	private String Created_TS;
	private String Updated_TS;
	public Coach(String coachID, String userID, String userName, String userHead, String age, String college,
			String seniority, String intro, String aCHV, String teachACHV, String verify, String created_TS, String updated_TS) {
		CoachID = coachID;
		UserID = userID;
		UserName = userName;
		UserHead = userHead;
		Age = age;
		College = college;
		Seniority = seniority;
		Intro = intro;
		ACHV = aCHV;
		TeachACHV = teachACHV;
		Verify = verify;
		Created_TS = created_TS;
		Updated_TS = updated_TS;
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
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserHead() {
		return UserHead;
	}
	public void setUserHead(String userHead) {
		UserHead = userHead;
	}
	public String getAge() {
		return Age;
	}
	public void setAge(String age) {
		Age = age;
	}
	public String getCollege() {
		return College;
	}
	public void setCollege(String college) {
		College = college;
	}
	public String getSeniority() {
		return Seniority;
	}
	public void setSeniority(String seniority) {
		Seniority = seniority;
	}
	public String getIntro() {
		return Intro;
	}
	public void setIntro(String intro) {
		Intro = intro;
	}
	public String getACHV() {
		return ACHV;
	}
	public void setACHV(String aCHV) {
		ACHV = aCHV;
	}
	public String getVerify() {
		return Verify;
	}
	public void setVerify(String verify) {
		Verify = verify;
	}
	public String getTeachACHV() {
		return TeachACHV;
	}
	public void setTeachACHV(String teachACHV) {
		TeachACHV = teachACHV;
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
