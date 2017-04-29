package com.galaxy.ggolf.domain;

public class ClubScore {
	private String UID;
	private String UserID;
	private String UserName;
	private String UserHead;
	private String PhoneNum;
	private String ClubID;
	private String DesignScore;
	private String GrassScore;
	private String FacilityScore;
	private String ServiceScore;
	private String Content;
	private String Created_TS;
	public ClubScore(String uID, String userID, String userName, String userHead, String phoneNum, String clubID,
			String designScore, String grassScore, String facilityScore, String serviceScore, String content,
			String created_TS) {
		UID = uID;
		UserID = userID;
		UserName = userName;
		UserHead = userHead;
		PhoneNum = phoneNum;
		ClubID = clubID;
		DesignScore = designScore;
		GrassScore = grassScore;
		FacilityScore = facilityScore;
		ServiceScore = serviceScore;
		Content = content;
		Created_TS = created_TS;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
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
	public String getPhoneNum() {
		return PhoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}
	public String getClubID() {
		return ClubID;
	}
	public void setClubID(String clubID) {
		ClubID = clubID;
	}
	public String getDesignScore() {
		return DesignScore;
	}
	public void setDesignScore(String designScore) {
		DesignScore = designScore;
	}
	public String getGrassScore() {
		return GrassScore;
	}
	public void setGrassScore(String grassScore) {
		GrassScore = grassScore;
	}
	public String getFacilityScore() {
		return FacilityScore;
	}
	public void setFacilityScore(String facilityScore) {
		FacilityScore = facilityScore;
	}
	public String getServiceScore() {
		return ServiceScore;
	}
	public void setServiceScore(String serviceScore) {
		ServiceScore = serviceScore;
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
