package com.galaxy.ggolf.domain;

import java.util.Collection;

public class Coach {
	private String UID;
	private String CoachID;
	private String CoachName;
	private String CoachHead;
	private String Age;
	private String ClubID;
	private String ClubName;
	private String Seniority;
	private String Intro;
	private String ACHV;
	private String TeachACHV;
	private String Verify;
	private String TeachLocation;
	private String TeachCollege;
	private String TeachAddress;
	private String Created_TS;
	private String Updated_TS;
	private CoachScore coachScore;
	private Collection<CoachComment> coachComments;
	
	public Coach(String uID, String coachID, String coachName, String coachHead, String age, String clubID,
			String clubName, String seniority, String intro, String aCHV, String teachACHV, String verify,
			String teachLocation, String teachCollege, String teachAddress, String created_TS, String updated_TS) {
		UID = uID;
		CoachID = coachID;
		CoachName = coachName;
		CoachHead = coachHead;
		Age = age;
		ClubID = clubID;
		ClubName = clubName;
		Seniority = seniority;
		Intro = intro;
		ACHV = aCHV;
		TeachACHV = teachACHV;
		Verify = verify;
		TeachLocation = teachLocation;
		TeachCollege = teachCollege;
		TeachAddress = teachAddress;
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
	public String getAge() {
		return Age;
	}
	public void setAge(String age) {
		Age = age;
	}
	public String getClubID() {
		return ClubID;
	}
	public void setClubID(String clubID) {
		ClubID = clubID;
	}
	public String getClubName() {
		return ClubName;
	}
	public void setClubName(String clubName) {
		ClubName = clubName;
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
	public String getTeachACHV() {
		return TeachACHV;
	}
	public void setTeachACHV(String teachACHV) {
		TeachACHV = teachACHV;
	}
	public String getVerify() {
		return Verify;
	}
	public void setVerify(String verify) {
		Verify = verify;
	}
	public String getTeachLocation() {
		return TeachLocation;
	}
	public void setTeachLocation(String teachLocation) {
		TeachLocation = teachLocation;
	}
	public String getTeachCollege() {
		return TeachCollege;
	}
	public void setTeachCollege(String teachCollege) {
		TeachCollege = teachCollege;
	}
	public String getTeachAddress() {
		return TeachAddress;
	}
	public void setTeachAddress(String teachAddress) {
		TeachAddress = teachAddress;
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
	public CoachScore getCoachScore() {
		return coachScore;
	}
	public void setCoachScore(CoachScore coachScore) {
		this.coachScore = coachScore;
	}
	public Collection<CoachComment> getCoachComments() {
		return coachComments;
	}
	public void setCoachComments(Collection<CoachComment> coachComments) {
		this.coachComments = coachComments;
	}
	
	
	
	
	
	
	
}
